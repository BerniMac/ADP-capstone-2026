package za.ac.cput.ecommerce.controller;
/*

  Author: Joshua Jonathan Bird - 230444032
  Date: 19/07/2026

*/

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;
//import za.ca.cput.commerce.domain.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static OrderItem orderItem;

    private String getUrl() {
        return "http://localhost:" + port + "/api/order-items";
    }

    @BeforeAll
    static void setup() {
        Order order = new Order.Builder()
                .setOrderId("ORDER001")
                .build();

        Product product = new Product.Builder()
                .setProductId("PROD001")
                .setProductName("Gaming Laptop")
                .build();

        orderItem = new OrderItem.Builder()
                .setOrderItemId("ITEM001")
                .setOrder(order)
                .setProduct(product)
                .setQuantity(2)
                .setPriceAtPurchase(25000.00)
                .build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<OrderItem> response =
                restTemplate.postForEntity(url, orderItem, OrderItem.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        orderItem = response.getBody();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        String url = getUrl() + "/" + orderItem.getOrderItemId();

        ResponseEntity<OrderItem> response =
                restTemplate.getForEntity(url, OrderItem.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getOrderItemId(),
                response.getBody().getOrderItemId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        OrderItem updatedOrderItem = new OrderItem.Builder()
                .setOrderItemId(orderItem.getOrderItemId())
                .setOrder(orderItem.getOrder())
                .setProduct(orderItem.getProduct())
                .setQuantity(3)
                .setPriceAtPurchase(25000.00)
                .build();

        String url = getUrl() + "/" + orderItem.getOrderItemId();

        ResponseEntity<OrderItem> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updatedOrderItem),
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        orderItem = response.getBody();
        assertEquals(3, orderItem.getQuantity());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<OrderItem[]> response =
                restTemplate.getForEntity(url, OrderItem[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getByOrder() {
        String url = getUrl() + "/order/"
                + orderItem.getOrder().getOrderId();

        ResponseEntity<OrderItem[]> response =
                restTemplate.getForEntity(url, OrderItem[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void getByProduct() {
        String url = getUrl() + "/product/"
                + orderItem.getProduct().getProductId();

        ResponseEntity<OrderItem[]> response =
                restTemplate.getForEntity(url, OrderItem[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void delete() {
        String url = getUrl() + "/" + orderItem.getOrderItemId();

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}



