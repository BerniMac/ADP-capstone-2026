package za.ca.cput.commerce.service;

/*
Author: Plamedie 230082629
12/07/2026
 */


import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;

import java.util.List;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory getInventoryById(String inventoryId);

    List<Inventory> getAllInventory();

    Inventory updateInventory(String inventoryId, Inventory inventory);

    void deleteInventory(String inventoryId);

    Inventory getInventoryByProductId(String productId);
}
