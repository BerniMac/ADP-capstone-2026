/*
 * ProductFactoryTest.java
 * TDD Test for Product Factory
 * Author: Plamedie 230082629
 * Date: 24 March 2026
 */
package factory;

import za.ca.cput.commerce.domain.Product;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.ProductFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFactoryTest {

    @Test
    public void testBuildProductSuccess() {
        Product product = ProductFactory.createProduct(
                "P001",
                "Laptop",
                999.99
        );

        assertNotNull(product);
        assertEquals("P001", product.getProductId());
        assertEquals("Laptop", product.getProductName());
        assertEquals("Gaming Laptop", product.getDescription());
        assertEquals(999.99, product.getCurrentPrice());
    }

    @Test
    public void testBuildProductNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct(null, "Laptop",  999.99);
        });
    }

    @Test
    public void testBuildProductEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("", "Laptop",  999.99);
        });
    }

    @Test
    public void testBuildProductNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("P001", null,  999.99);
        });
    }

    @Test
    public void testBuildProductNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("P001", "Laptop",  -100.00);
        });
    }
}
