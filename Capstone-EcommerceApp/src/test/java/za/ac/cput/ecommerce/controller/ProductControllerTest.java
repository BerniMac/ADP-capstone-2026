package za.ac.cput.ecommerce.controller;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.factory.ProductFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Product product;

    private String getUrl() {
        return "http://localhost:" + port + "/api/products";
    }

    @BeforeAll
    static void setup() {
        product = ProductFactory.createProduct(
                "Gaming Laptop",
                "RTX 5090",
                25999.99
        );
    }

    @Test
    @Order(1)
    void createProduct() {
        String url = getUrl();

        ResponseEntity<Product> response =
                restTemplate.postForEntity(url, product, Product.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        product = response.getBody();
    }

    @Test
    @Order(2)
    void getProductById() {
        String url = getUrl() + "/" + product.getProductId();

        ResponseEntity<Product> response =
                restTemplate.getForEntity(url, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(
                product.getProductId(),
                response.getBody().getProductId()
        );
    }

    @Test
    @Order(3)
    void updateProduct() {
        Product updatedProduct = new Product.Builder()
                .setProductId(product.getProductId())
                .setProductName("Gaming Laptop Updated")
                .setDescription("RTX 5090")
                .setCurrentPrice(27999.99)
                .build();

        String url = getUrl() + "/" + product.getProductId();

        ResponseEntity<Product> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updatedProduct),
                Product.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        product = response.getBody();

        assertEquals("Gaming Laptop Updated", product.getProductName());
    }

    @Test
    @Order(4)
    void getAllProducts() {
        String url = getUrl();

        ResponseEntity<Product[]> response =
                restTemplate.getForEntity(url, Product[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(5)
    void searchProducts() {
        String url = getUrl() + "/search?productName=Gaming";

        ResponseEntity<Product[]> response =
                restTemplate.getForEntity(url, Product[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void deleteProduct() {
        String url = getUrl() + "/" + product.getProductId();

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

