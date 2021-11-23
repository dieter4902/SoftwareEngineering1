package system.impl;

import datamodel.Order;
import system.Calculator;
import system.Formatter;
import system.Printer;

class PrinterImpl implements Printer {

    Calculator calc;

    public PrinterImpl(Calculator calculator) {
        this.calc = calculator;
    }

    @Override
    public StringBuffer printOrders(Iterable<Order> orders) {
        return null;//TODO
    }

    @Override
    public StringBuffer printOrder(Order order) {
        return null;//TODO
    }

    @Override
    public Formatter createFormatter() {
        return new FormatterImpl();
    }
}
