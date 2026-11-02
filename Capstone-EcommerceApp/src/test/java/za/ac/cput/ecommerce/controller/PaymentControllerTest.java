package za.ac.cput.ecommerce.controller;

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
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.domain.Payment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Payment payment;

    private String getUrl() {
        return "http://localhost:" + port + "/api/payments";
    }

    @BeforeAll
    static void setup() {
        Order order = new Order.Builder()
                .setOrderId("ORD001")
                .build();

        Card card = new Card.Builder()
                .setCardId("CARD001")
                .build();

        payment = new Payment.Builder()
                .setPaymentId("PAY001")
                .setOrder(order)
                .setPaymentAmount(2500)
                .setPaymentMethod("Credit Card")
                .setCard(card)
                .build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Payment> response =
                restTemplate.postForEntity(url, payment, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        payment = response.getBody();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void getById() {
        String url = getUrl() + "/" + payment.getPaymentId();

        ResponseEntity<Payment> response =
                restTemplate.getForEntity(url, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(payment.getPaymentId(),
                response.getBody().getPaymentId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        Payment updatedPayment = new Payment.Builder()
                .setPaymentId(payment.getPaymentId())
                .setOrder(payment.getOrder())
                .setPaymentAmount(3000)
                .setPaymentMethod(payment.getPaymentMethod())
                .setCard(payment.getCard())
                .build();

        String url = getUrl() + "/" + payment.getPaymentId();

        ResponseEntity<Payment> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updatedPayment),
                Payment.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        payment = response.getBody();
        assertEquals(3000, payment.getPaymentAmount());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<Payment[]> response =
                restTemplate.getForEntity(url, Payment[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getByCard() {
        String url = getUrl() + "/card/"
                + payment.getCard().getCardId();

        ResponseEntity<Payment[]> response =
                restTemplate.getForEntity(url, Payment[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void getByOrder() {
        String url = getUrl() + "/order/"
                + payment.getOrder().getOrderId();

        ResponseEntity<Payment> response =
                restTemplate.getForEntity(url, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void delete() {
        String url = getUrl() + "/" + payment.getPaymentId();

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}


