package za.ac.cput.ecommerce.controller;
/*
 * ShipmentFactoryTest.java
 * Author: Tlangelani Chauke
 * Date:19 july 2026
*/



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
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

import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Shipment;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShipmentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Order order;
    private static Shipment shipment;

    private String getUrl() {
        return "http://localhost:" + port + "/api/shipments";
    }

    @BeforeAll
    static void setup() {
        order = new Order.Builder()
                .setOrderId("ORD001")
                .build();

        shipment = new Shipment.Builder()
                .setShipmentId("SHIP001")
                .setAddress("123 Main Street")
                .setShipmentDate(LocalDateTime.now())
                .setDeliveryDate(LocalDateTime.now().plusDays(3))
                .setStatus("Shipped")
                .setOrder(order)
                .build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Shipment> response =
                restTemplate.postForEntity(url, shipment, Shipment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        shipment = response.getBody();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        String url = getUrl() + "/" + shipment.getShipmentId();

        ResponseEntity<Shipment> response =
                restTemplate.getForEntity(url, Shipment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(
                shipment.getShipmentId(),
                response.getBody().getShipmentId()
        );
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        Shipment updatedShipment = new Shipment.Builder()
                .copy(shipment)
                .setAddress("456 New Street")
                .setStatus("In Transit")
                .build();

        String url = getUrl() + "/" + shipment.getShipmentId();

        ResponseEntity<Shipment> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updatedShipment),
                Shipment.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        shipment = response.getBody();

        assertEquals("456 New Street", shipment.getAddress());
        assertEquals("In Transit", shipment.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<Shipment[]> response =
                restTemplate.getForEntity(url, Shipment[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getByOrderId() {
        String url = getUrl() + "/order/" + order.getOrderId();

        ResponseEntity<Shipment> response =
                restTemplate.getForEntity(url, Shipment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(
                shipment.getShipmentId(),
                response.getBody().getShipmentId()
        );
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void delete() {
        String url = getUrl() + "/" + shipment.getShipmentId();

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

