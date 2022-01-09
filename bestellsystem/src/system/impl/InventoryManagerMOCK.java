package system.impl;

import datamodel.Order;
import system.InventoryManager;

class InventoryManagerMOCK implements InventoryManager {

    private static InventoryManager inventoryManager = null;

    @Override
    public boolean isFillable(Order order) {
        return true;
    }

    public static InventoryManager getInstance() {
        if (inventoryManager == null) {
            inventoryManager = new InventoryManagerMOCK();
        }
        return inventoryManager;
    }
}
