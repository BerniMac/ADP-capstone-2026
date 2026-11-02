package za.ac.cput.ecommerce.controller;
/*
   Author: 222709006 Qhama dyushu
   Date: 19 July 2026
*/

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
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.factory.AddressFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Address address;

    @BeforeAll
    static void setup() {

        Customer customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John")
                .setEmail("j@Doe.com")
                .build();

        address = AddressFactory.createAddress(
                customer,
                "123 Main Street",
                "Cape Town",
                "Western Cape",
                "8001",
                "South Africa",
                "Home"
        );
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/addresses";
    }

    @Test
    @Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Address> response =
                restTemplate.postForEntity(
                        url,
                        address,
                        Address.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        address = response.getBody();
    }

    @Test
    @Order(2)
    void findAll() {
        String url = getUrl();

        ResponseEntity<Address[]> response =
                restTemplate.getForEntity(
                        url,
                        Address[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void findById() {
        String url = getUrl() + "/" + address.getAddressId();

        ResponseEntity<Address> response =
                restTemplate.getForEntity(
                        url,
                        Address.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    void update() {
        String url = getUrl() + "/" + address.getAddressId();

        Address updated = AddressFactory.createAddress(
                address.getCustomer(),
                "456 Updated Street",
                "Cape Town",
                "Western Cape",
                "8001",
                "South Africa",
                "Home"
        );

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Address> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Address> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Address.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        address = response.getBody();
    }

    @Test
    @Order(5)
    void findByCustomerId() {
        String customerId =
                address.getCustomer().getCustomerId();

        String url = getUrl() + "/customer/" + customerId;

        ResponseEntity<Address[]> response =
                restTemplate.getForEntity(
                        url,
                        Address[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void delete() {
        String url = getUrl() + "/" + address.getAddressId();

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
