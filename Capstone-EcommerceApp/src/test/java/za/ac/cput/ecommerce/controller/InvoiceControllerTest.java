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
import za.ca.cput.commerce.domain.Invoice;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Invoice invoice;

    @BeforeAll
    static void setup() {
        invoice = new Invoice.Builder()
                .setInvoiceId("INV001")
                .setInvoiceDate(LocalDateTime.now())
                .setTotalAmount(1500.00)
                .setTaxAmount(225.00)
                .setInvoiceStatus("PAID")
                .build();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/invoices";
    }

    @Test
    @Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Invoice> response =
                restTemplate.postForEntity(
                        url,
                        invoice,
                        Invoice.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        invoice = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = getUrl() + "/" + invoice.getInvoiceId();

        ResponseEntity<Invoice> response =
                restTemplate.getForEntity(
                        url,
                        Invoice.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        String url = getUrl() + "/" + invoice.getInvoiceId();

        Invoice updated = new Invoice.Builder()
                .copy(invoice)
                .setTotalAmount(1800.00)
                .setTaxAmount(270.00)
                .setInvoiceStatus("PAID")
                .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Invoice> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Invoice> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Invoice.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        invoice = response.getBody();
    }

    @Test
    @Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<Invoice[]> response =
                restTemplate.getForEntity(
                        url,
                        Invoice[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void getByOrderId() {
        // An existing Order must be associated with the invoice.
        String orderId = "ORD001";

        String url = getUrl() + "/order/" + orderId;

        ResponseEntity<Invoice> response =
                restTemplate.getForEntity(
                        url,
                        Invoice.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void delete() {
        String url = getUrl() + "/" + invoice.getInvoiceId();

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



