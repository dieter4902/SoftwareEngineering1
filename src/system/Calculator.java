package system;

import datamodel.Order;
import datamodel.TAX;

/**
 * Public interface of calculator that contains method to calculate total prices and VAT on Orders
 */
public interface Calculator {

    /**
     * Method that returns the value of an Order Iterable
     *
     * @param orders iterable with orders to be processed
     * @return long value corresponding to the sum of the orders content value
     */
    long calculateValue(Iterable<Order> orders);


    /**
     * Method that returns the value of a single Order
     *
     * @param order of which the value shall be calculated
     * @return long value corresponding to the value of the order
     */
    long calculateValue(Order order);


    /**
     * Method that calculates the sum of the VAT from orders contained in an Iterable
     *
     * @param orders iterable with orders to be processed
     * @return long value corresponding to the sum of the VAT
     */
    long calculateIncludedVAT(Iterable<Order> orders);


    /**
     * Method that calculated the Vat for a single given Order
     *
     * @param order of which the VAT shall be calculated for
     * @return long value corresponding to the VAT of the Order
     */
    long calculateIncludedVAT(Order order);

    /**
     * Method that calculates the VAT given price and VAT rate
     *
     * @param price   long value corresponding to the price of an Item
     * @param taxRate TAX Object in which the VAT rate of the item is stored
     * @return long value corresponding to the VAT given total price and taxRate
     */
    long calculateIncludedVAT(long price, TAX taxRate);

}
