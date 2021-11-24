package system.impl;

import datamodel.Currency;
import datamodel.Order;
import datamodel.OrderItem;
import system.Calculator;
import system.Formatter;
import system.Printer;

import java.util.HashMap;

class PrinterImpl implements Printer {

    Calculator calc;
    private long totalAllOrders;
    private long totalVAT;
    Formatter.OrderTableFormatter otfmt;
    HashMap<Long, Integer> customerCount;

    public PrinterImpl(Calculator calculator) {
        this.calc = calculator;
        totalAllOrders = 0;
        totalVAT = 0;
    }

    @Override
    public StringBuffer printOrders(Iterable<Order> orders) {
        otfmt = new OrderTableFormatterImpl(createFormatter(), new Object[][]{
                // five column table with column specs: width and alignment ('[' left, ']' right)
                {12, '['}, {20, '['}, {36, '['}, {10, ']'}, {10, ']'}
        })
                .liner("+-+-+-+-+-+")        // print table header
                .hdr("||", "Order-Id", "Customer", "Ordered Items", "Order", "MwSt.")
                .hdr("||", "", "", "", "Value", "incl.")
                .liner("+-+-+-+-+-+")
                .liner("||");


        StringBuffer sb = new StringBuffer();
        for (Order e : orders) {
            sb.append(printOrder(e));
        }

        otfmt.lineTotal(totalAllOrders, totalVAT, Currency.EUR);
        return sb;
    }

    @Override
    public StringBuffer printOrder(Order order) {

        StringBuffer sb = new StringBuffer();

        long[] prices = new long[2];
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
        return sb;

    }

    @Override
    public Formatter createFormatter() {
        return new FormatterImpl();
    }

    String getOrderSuffix(Order order) {
        int count = 1;
        long id = order.getCustomer().getId();
        count += customerCount.containsKey(id) ? customerCount.get(id) : 0;
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
