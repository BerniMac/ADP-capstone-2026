package za.ac.cput.ecommerce.controller;
/*
   Author: isheanesu chowuraya (223182192)
   Date: 19 july 2026
*/


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Review review;

    private String getUrl() {
        return "http://localhost:" + port + "/api/reviews";
    }

    @BeforeAll
    static void setup() {
        Customer customer = new Customer.Builder()
                .setCustomerId("C001")
                .build();

        Product product = new Product.Builder()
                .setProductId("P001")
                .build();

        review = new Review.Builder()
                .setReviewId("R001")
                .setCustomer(customer)
                .setProduct(product)
                .setRating(5)
                .setComment("Excellent product")
                .build();
    }

    @Test
    @Order(1)
    void create() {
        String url = getUrl();

        ResponseEntity<Review> response =
                restTemplate.postForEntity(url, review, Review.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        review = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = getUrl() + "/" + review.getReviewId();

        ResponseEntity<Review> response =
                restTemplate.getForEntity(url, Review.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(
                review.getReviewId(),
                response.getBody().getReviewId()
        );
    }

    @Test
    @Order(3)
    void update() {
        Review updatedReview = new Review.Builder()
                .setReviewId(review.getReviewId())
                .setCustomer(review.getCustomer())
                .setProduct(review.getProduct())
                .setRating(4)
                .setComment("Very good product")
                .build();

        String url = getUrl() + "/" + review.getReviewId();

        ResponseEntity<Review> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updatedReview),
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        review = response.getBody();

        assertEquals(4, review.getRating());
        assertEquals("Very good product", review.getComment());
    }

    @Test
    @Order(4)
    void getAll() {
        String url = getUrl();

        ResponseEntity<Review[]> response =
                restTemplate.getForEntity(url, Review[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(5)
    void getReviewsByProduct() {
        String url = getUrl() + "/product/"
                + review.getProduct().getProductId();

        ResponseEntity<Review[]> response =
                restTemplate.getForEntity(url, Review[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void getReviewsByCustomer() {
        String url = getUrl() + "/customer/"
                + review.getCustomer().getCustomerId();

        ResponseEntity<Review[]> response =
                restTemplate.getForEntity(url, Review[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(7)
    void delete() {
        String url = getUrl() + "/" + review.getReviewId();

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

