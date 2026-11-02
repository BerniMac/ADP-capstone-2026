package service;

/*
Author: Plamedie 230082629
12/07/2026
 */


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    private static final String PRODUCT_ID = "PROD333";
    private static final String PRODUCT_NAME = "Test Laptop";
    private static final String DESCRIPTION = "Laptop for testing";
    private static final double PRICE = 15000.00;

    private static Product buildProduct() {
        return new Product.Builder()
                .setProductId(PRODUCT_ID)
                .setProductName(PRODUCT_NAME)
                .setDescription(DESCRIPTION)
                .setCurrentPrice(PRICE)
                .build();
    }

    private static final Product product = buildProduct();

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        Product created = productService.create(product);

        assertNotNull(created);
        assertEquals(PRODUCT_NAME, created.getProductName());
        assertEquals(DESCRIPTION, created.getDescription());
        assertEquals(PRICE, created.getCurrentPrice());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void createNullProduct() {
        Product created = productService.create(null);

        assertNull(created);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void createProductWithBlankName() {
        Product invalidProduct = new Product.Builder()
                .setProductName(" ")
                .setDescription(DESCRIPTION)
                .setCurrentPrice(PRICE)
                .build();

        assertNull(productService.create(invalidProduct));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void createProductWithBlankDescription() {
        Product invalidProduct = new Product.Builder()
                .setProductName("Invalid Description Product")
                .setDescription(" ")
                .setCurrentPrice(PRICE)
                .build();

        assertNull(productService.create(invalidProduct));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void createProductWithInvalidPrice() {
        Product invalidProduct = new Product.Builder()
                .setProductName("Invalid Price Product")
                .setDescription(DESCRIPTION)
                .setCurrentPrice(0)
                .build();

        assertNull(productService.create(invalidProduct));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void createDuplicateProduct() {
        Product duplicateProduct = new Product.Builder()
                .setProductName(PRODUCT_NAME)
                .setDescription("Duplicate product description")
                .setCurrentPrice(20000.00)
                .build();

        assertNull(productService.create(duplicateProduct));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getById() {
        Product found = productService.getById(PRODUCT_ID);

        assertNotNull(found);
        assertEquals(PRODUCT_ID, found.getProductId());
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void getAll() {
        List<Product> products = productService.getAll();

        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void update() {
        Product updatedProduct = new Product.Builder()
                .copy(product)
                .setProductName("Updated Test Laptop")
                .setDescription("Updated laptop description")
                .setCurrentPrice(18000.00)
                .build();

        Product updated = productService.update(PRODUCT_ID, updatedProduct);

        assertNotNull(updated);
        assertEquals("Updated Test Laptop", updated.getProductName());
        assertEquals("Updated laptop description", updated.getDescription());
        assertEquals(18000.00, updated.getCurrentPrice());
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void searchByName() {
        List<Product> products = productService.searchByName("Updated Test Laptop");

        assertNotNull(products);
        assertFalse(products.isEmpty());

    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void delete() {
        productService.delete(PRODUCT_ID);

        assertNull(productService.getById(PRODUCT_ID));
    }
}