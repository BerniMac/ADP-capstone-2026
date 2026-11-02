package za.ac.cput.ecommerce.controller;
/*
   Author: Plamedie 230082629
   Date: 19 july 2026
*/

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Inventory inventory;

    @BeforeAll
    static void setup() {
        Product product = new Product.Builder()
                .setProductId("P001")
                .setProductName("Laptop")
                .build();

        inventory = new Inventory.Builder()
                .setInventoryId("I001")
                .setProduct(product)
                .setStockQuantity(50)
                .setWarehouseLocation("Warehouse A")
                .setLastUpdated(LocalDateTime.now())
                .build();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/inventory";
    }

    @Test
    @Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Inventory> response =
                restTemplate.postForEntity(
                        url,
                        inventory,
                        Inventory.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        inventory = response.getBody();
    }

    @Test
    @Order(2)
    void getInventoryById() {
        String url = getUrl() + "/" + inventory.getInventoryId();

        ResponseEntity<Inventory> response =
                restTemplate.getForEntity(
                        url,
                        Inventory.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void getAllInventory() {
        String url = getUrl();

        ResponseEntity<Inventory[]> response =
                restTemplate.getForEntity(
                        url,
                        Inventory[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    void updateInventory() {
        String url = getUrl() + "/" + inventory.getInventoryId();

        Inventory updated = new Inventory.Builder()
                .setInventoryId(inventory.getInventoryId())
                .setProduct(inventory.getProduct())
                .setStockQuantity(75)
                .setWarehouseLocation("Warehouse B")
                .setLastUpdated(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Inventory> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Inventory> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Inventory.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        inventory = response.getBody();
    }

    @Test
    @Order(5)
    void getInventoryByProduct() {
        String url = getUrl() + "/product/"
                + inventory.getProduct().getProductId();

        ResponseEntity<Inventory> response =
                restTemplate.getForEntity(
                        url,
                        Inventory.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void deleteInventory() {
        String url = getUrl() + "/" + inventory.getInventoryId();

        HttpEntity<String> entity =
                new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.DELETE,
                        entity,
                        Void.class
                );

        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        assertNull(response.getBody());
    }
}
