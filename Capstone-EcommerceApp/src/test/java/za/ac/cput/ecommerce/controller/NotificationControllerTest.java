package za.ac.cput.ecommerce.controller;


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
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Notification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Customer customer;
    private static Notification notification;

    @BeforeAll
    static void setup() {
        customer = new Customer.Builder()
                .setCustomerId("CUST001")
                .build();

        notification = new Notification.Builder()
                .setNotificationId("NOT001")
                .setCustomer(customer)
                .setMessage("Order has been shipped.")
                .setStatus("UNREAD")
                .setNotificationDate(LocalDateTime.now())
                .build();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/notifications";
    }

    @Test
    @Order(1)
    void createNotification() {
        String url = getUrl();

        ResponseEntity<Notification> response =
                restTemplate.postForEntity(
                        url,
                        notification,
                        Notification.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        notification = response.getBody();
    }

    @Test
    @Order(2)
    void getNotification() {
        String url = getUrl() + "/"
                + notification.getNotificationId();

        ResponseEntity<Notification> response =
                restTemplate.getForEntity(
                        url,
                        Notification.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void updateNotification() {
        String url = getUrl() + "/"
                + notification.getNotificationId();

        Notification updated = new Notification.Builder()
                .copy(notification)
                .setMessage("Your order has been delivered.")
                .setStatus("READ")
                .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Notification> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Notification> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Notification.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        notification = response.getBody();
    }

    @Test
    @Order(4)
    void getAllNotifications() {
        String url = getUrl();

        ResponseEntity<Notification[]> response =
                restTemplate.getForEntity(
                        url,
                        Notification[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void getNotificationsByCustomer() {
        String url = getUrl() + "/customer/"
                + customer.getCustomerId();

        ResponseEntity<Notification[]> response =
                restTemplate.getForEntity(
                        url,
                        Notification[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void deleteNotification() {
        String url = getUrl() + "/"
                + notification.getNotificationId();

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
