package za.ac.cput.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Customer customer;

    @BeforeAll
    static void setup() {
        customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("0123456789")
                .build();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/customers";
    }

    @Test
    @Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Customer> response =
                restTemplate.postForEntity(
                        url,
                        customer,
                        Customer.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        customer = response.getBody();
    }

    @Test
    @Order(2)
    void getCustomer() {
        String url = getUrl() + "/" + customer.getCustomerId();

        ResponseEntity<Customer> response =
                restTemplate.getForEntity(
                        url,
                        Customer.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        String url = getUrl() + "/" + customer.getCustomerId();

        Customer updated = new Customer.Builder()
                .setCustomerId(customer.getCustomerId())
                .setName("John Smith")
                .setEmail("johnsmith@example.com")
                .setPhone("0823456789")
                .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Customer> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Customer> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Customer.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        customer = response.getBody();
    }

    @Test
    @Order(4)
    void getAllCustomers() {
        String url = getUrl();

        ResponseEntity<Customer[]> response =
                restTemplate.getForEntity(
                        url,
                        Customer[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void getCustomerByEmail() {
        String url = getUrl() + "/email/" + customer.getEmail();

        ResponseEntity<Customer> response =
                restTemplate.getForEntity(
                        url,
                        Customer.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void searchCustomers() {
        String url = getUrl() + "/search?name=John";

        ResponseEntity<Customer[]> response =
                restTemplate.getForEntity(
                        url,
                        Customer[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(7)
    void deleteCustomer() {
        String url = getUrl() + "/" + customer.getCustomerId();

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
