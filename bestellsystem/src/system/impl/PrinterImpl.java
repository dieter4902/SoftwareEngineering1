package system.impl;

import datamodel.Currency;
import datamodel.Order;
import datamodel.OrderItem;
import system.Calculator;
import system.Formatter;
import system.Printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

class PrinterImpl implements Printer {

    Calculator calc;
    private long totalAllOrders;
    private long totalVAT;
    HashMap<Long, Integer> customerCount;
    Formatter formatter;
    OrderTableFormatterImpl otfmt;

    public PrinterImpl(Calculator calculator) {
        this.calc = calculator;
        totalAllOrders = 0;
        totalVAT = 0;
        customerCount = new HashMap<>();
        formatter = createFormatter();
    }


    @Override
    public StringBuffer printOrders(Iterable<Order> orders) {

        otfmt = (OrderTableFormatterImpl) new OrderTableFormatterImpl(createFormatter(), new Object[][]{

                {12, '['}, {20, '['}, {36, '['}, {10, ']'}, {10, ']'}
        })
                .liner("+-+-+-+-+-+")        // print table header
                .hdr("||", "Order-Id", "Customer", "Ordered Items", "Order", "MwSt.")
                .hdr("||", "", "", "", "Value", "incl.")
                .liner("+-+-+-+-+-+")
                .liner("||");

        for (Order e : orders) {
            printOrder(e);

        }
        otfmt.lineTotal(totalAllOrders, totalVAT, Currency.EUR);
        return otfmt.getFormatter().getBuffer();
    }

    @Override
    public StringBuffer printOrder(Order order) {


        boolean flag = true;
        long price, vat, totalPrice = 0, totalVat = 0;
        for (OrderItem e : order.getItems()) {
            price = e.getUnitsOrdered() * e.getArticle().getUnitPrice();
            vat = calc.calculateIncludedVAT(e.getArticle().getUnitPrice() * e.getUnitsOrdered(), e.getArticle().getTax());
            if (flag) {
                flag = false;

                otfmt.line(order.getId(), getOrderSuffix(order), e.toString(), price, vat);
            } else {
                otfmt.line("", "", e.toString(), price, vat);
            }
            totalPrice += price;
            totalVat += vat;
        }
        if (order.getItemsAsArray().length > 1) {
            otfmt.liner("| | |-|-|-|")
                    .line("", "", "total:", totalPrice, totalVat);
        }
        otfmt.liner("| | | |=|=|")
                .liner("| | | | | |");
        totalAllOrders += totalPrice;
        totalVAT += totalVat;
        return otfmt.getFormatter().getBuffer();

    }

    @Override
    public Formatter createFormatter() {
        return new FormatterImpl();
    }

    @Override
    public void printOrdersToFile(Iterable<Order> orders, String filepath) throws IOException {
        String data = printOrders(orders).toString();
        FileWriter fileWriter = new FileWriter(filepath);
        fileWriter.write(data);
        fileWriter.close();
    }

    String getOrderSuffix(Order order) {
        int count = 1;
        long id = order.getCustomer().getId();
        count += customerCount.getOrDefault(id, 0);
        customerCount.put(id, count);
        String s = order.getCustomer().getFirstName() + "'s ";
        switch (count) {
            case 0, 1://do nothing
                break;
            case 2:
                s += count + "nd ";
                break;
            case 3:
                s += count + "rd ";
                break;
            default:
                s += count + "th ";
                break;
        }
        s += "order:";
        return s;
    }
}
