package system.impl;

import datamodel.Article;
import datamodel.Order;
import system.InventoryManager;

import java.util.Optional;

class InventoryManagerMOCK implements InventoryManager {

    private static InventoryManager inventoryManager = null;

    @Override
    public int getUnitsInStock(String id) {
        return 0;
    }

    @Override
    public void update(String id, int updatedUnitsInStock) {

    }

    @Override
    public boolean isFillable(Order order) {
        return true;
    }

    @Override
    public boolean fill(Order order) {
        return false;
    }

    @Override
    public StringBuffer printInventory() {
        return null;
    }

    @Override
    public StringBuffer printInventory(int sortedBy, boolean decending, Integer... limit) {
        return null;
    }

    public static InventoryManager getInstance() {
        if (inventoryManager == null) {
            inventoryManager = new InventoryManagerMOCK();
        }
        return inventoryManager;
    }

    @Override
    public Optional<Article> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Iterable<Article> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Article save(Article entity) {
        return null;
    }
}
