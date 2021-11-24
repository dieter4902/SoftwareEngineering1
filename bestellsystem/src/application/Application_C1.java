package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import datamodel.*;


/**
 * Class with Customer and Order objects. Running the main() function prints
 * a list of orders using the OrderTableFormatter.
 *
 * @author AJ
 */

public class Application_C1 {

    /**
     * list with sample customers, List.of( eric, anne, tim, nadine, khaled )
     */
    final List<Customer> customers;

    /**
     * list with sample articles, List.of( aTasse, aBecher, aKanne, aTeller )
     */
    final List<Article> articles;

    /**
     * list with sample orders, List.of( o5234,  o8592,  o3563,  o6135 )
     */
    final List<Order> orders;

    HashMap<Long, Integer> customerCount;

    /**
     * Default constructor to initialize Article and Order objects,
     * Customer objects are inherited from base class.
     */
    Application_C1() {

        customerCount = new HashMap<>();
        // inherit Customers from base class
        Customer eric = new Customer("Eric Meyer")
                .setId(892474)    // set id, first time
                .setId(482947)    // ignored, since id can only be set once
                .addContact("eric98@yahoo.com")    // contact_1
                .addContact("(030) 3945-642298")    // contact_2
                .addContact("(030) 3945-642298");    // ignore duplicate

        Customer anne = new Customer("Bayer, Anne")
                .setId(643270)
                .addContact("anne24@yahoo.de")
                .addContact("(030) 3481-23352");

        Customer tim = new Customer("Tim Schulz-Mueller")
                .setId(286516)
                .addContact("tim2346@gmx.de");

        Customer nadine = new Customer("Nadine-Ulla Blumenfeld")
                .setId(412396)
                .addContact("+49 152-92454");

        Customer khaled = new Customer()
                .setName("Khaled Saad Mohamed Abdelalim")
                .setId(456454)
                .addContact("+49 1524-12948210");


        Customer lena = new Customer("Lena Neumann")
                .setId(556849)
                .addContact("lena228@gmail.com");

        //
        this.customers = new ArrayList<>(List.of(eric, anne, tim, nadine, khaled, lena));

        // sample articles to use in orders
        Article tasse = new Article("Tasse", 299).setId("SKU-458362");
        Article becher = new Article("Becher", 149).setId("SKU-693856");
        Article kanne = new Article("Kanne", 1999).setId("SKU-518957");
        Article teller = new Article("Teller", 649).setId("SKU-638035");
        //
        Article buch_Java = new Article("Buch \"Java\"", 4990).setId("SKU-278530")
                .setTax(TAX.GER_VAT_REDUCED);    // reduced tax rate for books
        //
        Article buch_OOP = new Article("Buch \"OOP\"", 7995).setId("SKU-425378")
                .setTax(TAX.GER_VAT_REDUCED);    // reduced tax rate for books

        Article pfanne = new Article("Pfanne", 4999).setId("SKU‐300926");
        Article fahrradhelm = new Article("Fahrradhelm", 16900).setId("SKU‐663942");
        Article fahrradkarte = new Article("Fahrradkarte", 695).setId("SKU‐583978")
                .setTax(TAX.GER_VAT_REDUCED);


        //
        this.articles = List.of(tasse, becher, kanne, teller, buch_Java, buch_OOP, pfanne, fahrradhelm, fahrradkarte);

        // Eric's 1st order
        Order o8592 = new Order(eric)    // new order for Eric
                .setId("8592356245")    // assign order-id: 8592356245
                // add items to order
                .addItem(teller, 4)    // 4 Teller, 4x 6.49 �
                .addItem(becher, 8)    // 8 Becher, 8x 1.49 �
                .addItem(buch_OOP, 1)    // 1 Buch "OOP", 1x 79.95 �, 7% MwSt (5.23�)
                .addItem(tasse, 4);    // 4 Tassen, 4x 2.99 �
        //
        // Anne's order
        Order o3563 = new Order(anne)
                .setId("3563561357")
                .addItem(teller, 2)
                .addItem(tasse, 2);
        //
        // Eric's 2nd order
        Order o5234 = new Order(eric)
                .setId("5234968294")
                .addItem(kanne, 1);
        //
        // Nadine's order
        Order o6135 = new Order(nadine)
                .setId("6135735635")
                .addItem(teller, 12)
                .addItem(buch_Java, 1)
                .addItem(buch_OOP, 1);
        //
        // Eric's 3rd order
        Order o7356 = new Order(eric)
                .setId("7356613535")
                .addItem(fahrradhelm, 1)
                .addItem(fahrradkarte, 1);
        //
        // Eric's 4th order
        Order o4450 = new Order(eric)
                .setId("4450735661")
                .addItem(tasse, 3)
                .addItem(becher, 3)
                .addItem(kanne, 1);
        //
        // Lena's order
        Order o6173 = new Order(lena)
                .setId("6173535635")
                .addItem(buch_Java, 1)
                .addItem(fahrradkarte, 1);
        //
        this.orders = new ArrayList<>(List.of(o8592, o3563, o5234, o6135, o7356, o4450, o6173));
    }


    /**
     * Public main function that creates instance of Application_B3 class
     * and runs the printCustomers() method.
     *
     * @param args standard argument vector passed from command line
     */
    public static void main(String[] args) {
        System.out.println("SE1 Bestellsystem\n");
        //
        Application_C1 app = new Application_C1();
        //
        app.printOrders(app.orders);
    }


    /**
     * Print list of orders using OrderTableFormatter.
     *
     * @param orders list of orders to print
     */
    void printOrders(List<Order> orders) {
        //
        OrderTableFormatter otfmt = new OrderTableFormatter(new Object[][]{
                // five column table with column specs: width and alignment ('[' left, ']' right)
                {12, '['}, {20, '['}, {36, '['}, {10, ']'}, {10, ']'}
        })
                .liner("+-+-+-+-+-+")        // print table header
                .hdr("||", "Order-Id", "Customer", "Ordered Items", "Order", "MwSt.")
                .hdr("||", "", "", "", "Value", "incl.")
                .liner("+-+-+-+-+-+")
                .liner("||");


        long totalAllOrders = 0;
        long totalVAT = 0;
        for (Order e : orders) {
            long[] l = printOrder(otfmt, e);
            totalAllOrders += l[0];
            totalVAT += l[1];
        }

        otfmt            // finalize table with compound value and VAT (MwSt.) of all orders
                .lineTotal(totalAllOrders, totalVAT, Currency.EUR)
                .print();    // output table
    }


    /**
     * Append content of order to OrderTableFormatter table.
     * <p>Example of lines appended to table:<pre>
     * |8592356245 |Eric's order: |4 Teller (SKU-638035), 4x 6.49�     |  25.96�|  4.14�|
     * |           |              |8 Becher (SKU-693856), 8x 1.49�     |  11.92�|  1.90�|
     * |           |              |1 Buch "OOP" (SKU-425378), 79.95�   |  79.95�|  5.23�|
     * |           |              |4 Tasse (SKU-458362), 4x 2.99�      |  11.96�|  1.91�|
     * |           |              |------------------------------------|--------|-------|
     * |           |              |total:                              | 129.79�| 13.18�|
     * |           |              |                                    |========|=======|
     * |           |              |                                    |        |       |</pre>
     *
     * @param otfmt OrderTableFormatter to collect content from order
     * @param order order to print
     */
    long[] printOrder(OrderTableFormatter otfmt, Order order) {

        long[] prices = new long[2];
        boolean flag = true;
        long price, vat, totalPrice = 0, totalVat = 0;
        for (OrderItem e : order.getItems()) {
            price = e.getUnitsOrdered() * e.getArticle().getUnitPrice();
            vat = calculateIncludedVAT(e.getArticle().getUnitPrice() * e.getUnitsOrdered(), e.getArticle().getTax());
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
        prices[0] = totalPrice;
        prices[1] = totalVat;
        return prices;
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

    /**
     * Calculates the compound value of all orders
     *
     * @param orders list of orders
     * @return compound value of all orders
     */
    long calculateValue(Iterable<Order> orders) {
        long value = 0;
        for (Order e : orders) {
            value += calculateValue(e);
        }
        return value;
    }

    /**
     * Calculates the compound VAT of all orders
     *
     * @param orders list of orders
     * @return compound VAT of all orders
     */
    long calculateIncludedVAT(Iterable<Order> orders) {
        long vat = 0;
        for (Order e : orders) {
            vat += calculateIncludedVAT(e);
        }
        return vat;
    }

    /**
     * Calculates the value of one order
     *
     * @param order order for which the value is calculated
     * @return value of the order
     */
    long calculateValue(Order order) {
        long value = 0;
        for (OrderItem e : order.getItems()) {
            value += e.getArticle().getUnitPrice() * e.getUnitsOrdered();
        }
        return value;
    }

    /**
     * Calculates the VAT of one order. VAT is based on the rate that
     * applies to the articles of each line item.
     *
     * @param order order for which the included VAT is calculated
     * @return compound VAT of all ordered items
     */
    long calculateIncludedVAT(Order order) {
        long vat = 0;
        for (OrderItem e : order.getItems()) {
            vat += calculateIncludedVAT(e.getArticle().getUnitPrice() * e.getUnitsOrdered(), e.getArticle().getTax());
        }
        return vat;
    }

    /*
     * private helper to calculate included VAT for a price based on
     * a TAX rate (as defined in Enum TAX).
     */
    private long calculateIncludedVAT(long price, TAX taxRate) {
        return Math.round(price / (100 + taxRate.rate()) * taxRate.rate());
    }



    /*
     * no changes needed in code below
     */


    /**
     * TableFormatter to format output for orders in form of a 5-column table.
     *
     * @author sgra64
     */
    class OrderTableFormatter extends TableFormatter {

        OrderTableFormatter(Object[][] descr) {
            super(descr);
        }

        OrderTableFormatter line(String orderId, String customerName, String orderedItems, long value, long vat) {
            String valueStr = fmtPaddedPrice(value, width[3], Currency.EUR).toString();
            String vatStr = fmtPaddedPrice(vat, width[4], Currency.EUR).toString();
            hdr(new String[]{        // use hdr() to output line
                    "||", orderId, customerName, orderedItems, valueStr, vatStr
            });
            return this;
        }

        OrderTableFormatter lineTotal(long value, long vat, Currency currency) {
            String valueStr = fmtPrice(value, currency).toString();
            String vatStr = fmtPrice(vat, currency).toString();
            return liner("+-+-+-+-+-+")
                    .hdr("", "", "total value (all orders):", "|", valueStr, "|", vatStr, "|")
                    .liner("      +-+-+");
        }

        OrderTableFormatter hdr(String... headers) {
            return (OrderTableFormatter) super.hdr(headers);
        }

        OrderTableFormatter liner(String... pattern) {
            return (OrderTableFormatter) super.liner(pattern);
        }

        OrderTableFormatter print() {
            return (OrderTableFormatter) super.print();
        }
    }


    StringBuffer fmtPrice(long price, Currency... currency) {
        StringBuffer sbPrice = new StringBuffer();
        Currency cur = currency.length > 0 ? currency[0] : Currency.NONE;
        boolean fractional = cur != Currency.YEN;    // always fractional, including NONE
        String str;
        if (fractional) {
            long cent = Math.abs(price % 100L);
            str = String.format("%,d.%02d%s", price / 100, cent, cur.symbol(1));
        } else {
            str = String.format("%,d%s", price, cur.symbol(1));
        }
        sbPrice.append(str);
        return sbPrice;
    }

    StringBuffer fmtPaddedPrice(long price, int width, Currency... currency) {
        StringBuffer sbPrice = fmtPrice(price, currency);
        if (sbPrice.length() > width) {    // cut price to width
            sbPrice.delete(width - (width >= 1 ? 1 : 0), sbPrice.length());
            sbPrice.append(width >= 1 ? "+" : " ");    // add '+' as cut-off String
        }
        return sbPrice;
    }


    /**
     * Formatter to format output as a table. A table is defined by colums
     * with:
     * - fixed width and alinment: '[' left-aligned, ']' right-aligned
     * <p>
     * Rows are added to a table with
     * - hdr()		- header line with header content
     * - line()	- line item with column content for one row
     * - liner()	- horizontal line with specs for separators "|", "+"
     * and fillers "-" for each column
     * <p>
     * Table content is collected in a StringBuffer.
     * - print()	- outputs the StringBuffer
     *
     * @author sgra64
     */
    class TableFormatter {
        final StringBuffer sb = new StringBuffer();
        final int cols;
        final int width[];
        final char align[];

        TableFormatter(Object[][] descr) {
            this.cols = descr.length;
            this.width = new int[cols];
            this.align = new char[cols];
            for (int i = 0; i < cols; i++) {
                this.width[i] = (int) descr[i][0];
                this.align[i] = (char) descr[i][1];
            }
        }

        /**
         * Append header content to OrderTableFormatter.
         * <p>Example of lines appended to table:<pre>
         *
         * .hdr( "Order-Id", "Customer", "Ordered Items", "Order" )
         * .hdr( "||", "Order-Id", "Customer", "Ordered Items", "Order" )
         * .hdr( "|", "Order-Id", "|", "Customer", "|", "Ordered Items", "|", "Order", "|" )
         *
         *  Order-Id     Customer           Ordered Items                   Order
         * |Order-Id    |Customer          |Ordered Items             |     Order|
         * |Order-Id    |Customer          |Ordered Items             |     Order|</pre>
         *
         * @param headers
         * @return chainable self reference
         */
        TableFormatter hdr(String... headers) {
            int len = headers.length;
            int col = 0;
            boolean allSeps = len > 0 && headers[0] != null && headers[0].equals("||");
            String sep_ = allSeps ? "|" : " ";
            String sep1 = sep_;
            for (int ih = allSeps ? 1 : 0; ih < len && (col < cols || headers[ih].length() == 1); ih++) {
                String text = headers[ih];
                if (!allSeps && text.length() == 1) {
                    sep1 = text;
                    continue;
                }
                sb.append(sep1);
                fmtPaddedText(sb, text, width[col], align[col]);
                sep1 = sep_;
                col++;
            }
            if (!sep1.equals(" ")) {
                sb.append(sep1);
            }
            sb.append("\n");
            return this;
        }

        TableFormatter liner(String... pattern) {
            String pat = pattern.length > 0 ? pattern[0] : null;
            int pi = 0;
            boolean compressedPattern = pat != null && pat.equals("||");
            String c12 = compressedPattern ? "| " : "+-";
            pat = compressedPattern ? null : pat;
            for (int i = 0; i < cols; i++) {
                sb.append(pat(pat, pi++, c12)).append(pat(pat, pi++, c12).repeat(width[i]));
            }
            sb.append(pat(pat, pi++, c12));
            sb.append("\n");
            return this;
        }

        TableFormatter print() {
            System.out.println(sb.toString());
            return this;
        }

        private String pat(String pat, int i, String c12) {
            String c = pat == null ? i % 2 == 0 ? String.valueOf(c12.charAt(0)) : String.valueOf(c12.charAt(1)) :
                    i < pat.length() ? String.valueOf(pat.charAt(i)) : "";
            return c;
        }

        private final String filler = " ";

        private StringBuffer fmtPaddedText(StringBuffer sb, String text, int width, char direction, String... cutoff) {
            String scut = cutoff.length > 0 ? (cutoff[0] == null ? "" : cutoff[0]) : "";
            boolean dir = direction == '[';
            text = text != null ? text : "";
            int len = text.length();
            int d = width - len;
            if (d >= 0) {    // insert fill spaces
                sb = dir ?
                        sb.append(text).append(filler.repeat(d)) :
                        sb.append(filler.repeat(d)).append(text);
                //
            } else {    // cut str to width
                d = -d;
                boolean showCutOffStr = d > scut.length();
                if (showCutOffStr) {
                    width -= scut.length();        // adjust for cutoff string
                    d += scut.length();
                }
                sb.append(!dir && showCutOffStr ? scut : "");
                sb = dir ?
                        sb.append(text.substring(0, width)) :
                        sb.append(text.substring(d, len));
                //
                sb.append(dir && showCutOffStr ? scut : "");
            }
            return sb;
        }
    }
}
