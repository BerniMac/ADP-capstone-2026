package za.ac.cput.ecommerce.controller;
/*

Author:isheanesu chowuraya 223182192
date:19 july 2026
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
import za.ca.cput.commerce.domain.Card;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Card card;

    @BeforeAll
    static void setup() {
        card = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John Smith")
                .setCardType("VISA")
                .setCardNumber("4111111111111111")
                .setCardExpiry("12/28")
                .setCardCVV("123")
                .build();
    }

    private String getUrl() {
        return "http://localhost:" + port + "/api/cards";
    }

    @Test
    @Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Card> response =
                restTemplate.postForEntity(
                        url,
                        card,
                        Card.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        card = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = getUrl() + "/" + card.getCardId();

        ResponseEntity<Card> response =
                restTemplate.getForEntity(
                        url,
                        Card.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        String url = getUrl();

        // Replace with your actual Card builder and fields.
        Card updated = card;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Card> entity =
                new HttpEntity<>(updated, headers);

        ResponseEntity<Card> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        entity,
                        Card.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        card = response.getBody();
    }

    @Test
    @Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<Card[]> response =
                restTemplate.getForEntity(
                        url,
                        Card[].class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void delete() {
        String url = getUrl() + "/" + card.getCardId();

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
