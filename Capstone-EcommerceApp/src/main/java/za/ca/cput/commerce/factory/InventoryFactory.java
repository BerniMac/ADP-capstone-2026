/* InventoryFactory.java
   InventoryFactory class
   Author: Plamedie 230082629
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;
import java.time.LocalDateTime;

public class InventoryFactory {

    public static Inventory createInventory(Product product,
                                            int stockQuantity,
                                            String warehouseLocation) {

        return new Inventory.Builder()
                .setProduct(product)
                .setStockQuantity(stockQuantity)
                .setWarehouseLocation(warehouseLocation)
                .setLastUpdated(LocalDateTime.now())
                .build();
    }
}