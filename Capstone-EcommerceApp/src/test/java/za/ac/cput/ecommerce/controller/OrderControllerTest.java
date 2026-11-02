package za.ac.cput.ecommerce.controller;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
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
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Customer customer;
    private static Order order;

    @BeforeAll
    static void setup() {
        customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John")
                .setEmail("john@example.com")
                .build();

        order = new Order.Builder()
                .setOrderId("O001")
                .setCustomer(customer)
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(2500.00)
                .build();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/orders";
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Order> response =
                restTemplate.postForEntity(
                        url,
                        order,
                        Order.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        order = response.getBody();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        String url = getUrl() + "/" + order.getOrderId();

        ResponseEntity<Order> response =
                restTemplate.getForEntity(
                        url,
                        Order.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        String url = getUrl() + "/" + order.getOrderId();

        Order updated = new Order.Builder()
                .copy(order)
                .setTotalAmount(3000.00)
                .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Order> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Order> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Order.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        order = response.getBody();
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<Order[]> response =
                restTemplate.getForEntity(
                        url,
                        Order[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getOrdersByCustomer() {
        String url = getUrl() + "/customer/"
                + customer.getCustomerId();

        ResponseEntity<Order[]> response =
                restTemplate.getForEntity(
                        url,
                        Order[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void delete() {
        String url = getUrl() + "/" + order.getOrderId();

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


