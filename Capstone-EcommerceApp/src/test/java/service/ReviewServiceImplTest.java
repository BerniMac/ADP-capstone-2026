package service;

/*
12/07/2026
Author: isheanesu chowuraya (223182192)
 */

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.service.ReviewService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    private static Review buildReview() {
        return new Review.Builder()
                .setReviewId("222")
                .build();
    }

    private static final Review review = buildReview();

    @Test
    @Order(1)
    void create() {
        Review created = reviewService.create(review);

        assertNotNull(created);
        assertEquals(review.getReviewId(), created.getReviewId());
    }

    @Test
    @Order(2)
    void read() {
        Optional<Review> read =
                reviewService.read(review.getReviewId());

        assertTrue(read.isPresent());
        assertEquals(
                review.getReviewId(),
                read.get().getReviewId()
        );
    }

    @Test
    @Order(3)
    void update() {
        Review reviewUpdated = new Review.Builder()
                .copy(review)
                .setReviewId(review.getReviewId())
                .build();

        Review updated = reviewService.update(reviewUpdated);

        assertNotNull(updated);
        assertEquals(
                reviewUpdated.getReviewId(),
                updated.getReviewId()
        );
    }

    @Test
    @Order(4)
    void getAll() {
        List<Review> reviews = reviewService.getAll();

        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
    }

    @Test
    @Order(5)
    void getReviewsByProduct() {
        List<Review> reviews =
                reviewService.getReviewsByProduct("111");

        assertNotNull(reviews);
    }

    @Test
    @Order(6)
    void getReviewsByCustomer() {
        List<Review> reviews =
                reviewService.getReviewsByCustomer("333");

        assertNotNull(reviews);
    }

    @Test
    @Order(7)
    void delete() {
        boolean deleted =
                reviewService.delete(review.getReviewId());

        assertTrue(deleted);

        Optional<Review> deletedReview =
                reviewService.read(review.getReviewId());

        assertFalse(deletedReview.isPresent());
    }
}
