package system.impl;

import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;
import system.Calculator;

class CalculatorImpl implements Calculator {

    @Override
    public long calculateValue(Iterable<Order> orders) {
        long value = 0;
        for (Order e : orders) {
            value += calculateValue(e);
        }
        return value;
    }

    @Override
    public long calculateValue(Order order) {
        long value = 0;
        for (OrderItem e : order.getItems()) {
            value += e.getArticle().getUnitPrice() * e.getUnitsOrdered();
        }
        return value;
    }

    @Override
    public long calculateIncludedVAT(Iterable<Order> orders) {
        long vat = 0;
        for (Order e : orders) {
            vat += calculateIncludedVAT(e);
        }
        return vat;
    }

    @Override
    public long calculateIncludedVAT(Order order) {
        long vat = 0;
        for (OrderItem e : order.getItems()) {
            vat += calculateIncludedVAT(e.getArticle().getUnitPrice() * e.getUnitsOrdered(), e.getArticle().getTax());
        }
        return vat;
    }

    @Override
    public long calculateIncludedVAT(long price, TAX taxRate) {
        return Math.round(price / (100 + taxRate.rate()) * taxRate.rate());
    }
}
