package system.impl;

import datamodel.Order;
import system.InventoryManager;

public class InventoryManagerMOCK implements InventoryManager {
    @Override
    public boolean isFillable(Order order) {
        return false;
    }
}
