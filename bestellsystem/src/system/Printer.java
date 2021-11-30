package system;

import datamodel.Order;

import java.io.IOException;

/**
 * Public interface of a Printer component that provides
 * methods to print Orders
 */
public interface Printer {
    /**
     * packages Order in a StringBuffer
     *
     * @param orders containing the orders to be included
     * @return StringBuffer containing the Orders
     */
    StringBuffer printOrders(Iterable<Order> orders);

    /**
     * @param order the order to be printed
     * @return StringBuffer containing the single Order
     */
    StringBuffer printOrder(Order order);

    /**
     * creator method for Formatter
     *
     * @return Formatter
     */
    Formatter createFormatter();

    /**
     * Print orders as table to a file.
     *
     * Conditions:
     *  ‐ creates new file or overwrites an existing file.
     *  ‐ not existing parts of the path are created, throws IOException
     *    if this is not possible.
     *
     * @param orders list of orders to print.
     * @param filepath path and name of the output file.
     * @throws IOException for errors.
     */
    void printOrdersToFile(Iterable<Order> orders, String filepath) throws IOException;
}
