/* InventoryFactoryTest.java
   InventoryFactoryTest class
   Author: Plamedie 230082629
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Inventory;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.factory.InventoryFactory;

import static org.junit.jupiter.api.Assertions.*;


class InventoryFactoryTest {

    private Product sampleProduct() {
        return new Product.Builder()
                .setProductId("PROD-001")
                .setProductName("Wireless Mouse")
                .setDescription("Ergonomic wireless mouse")
                .setCurrentPrice(29.99)
                .build();
    }

    @Test
    void testCreateInventorySuccess() {
        Inventory inventory = InventoryFactory.createInventory(
                sampleProduct(),           // was: "PROD-001"
                100,
                "2026-06-21"
        );

        assertNotNull(inventory);
        assertEquals("INV-505", inventory.getInventoryId());
        assertEquals("PROD-001", inventory.getProduct().getProductId());  // was: inventory.getProductId()
        assertEquals(100, inventory.getStockQuantity());
        assertEquals("Warehouse A", inventory.getWarehouseLocation());
        assertEquals("2026-06-21", inventory.getLastUpdated());
    }

    @Test
    void testCreateInventoryFail() {
        Inventory inventory = InventoryFactory.createInventory(
                sampleProduct(),
                -5, // invalid stock
                "2026-06-21"
        );
        assertNull(inventory);
    }
}
