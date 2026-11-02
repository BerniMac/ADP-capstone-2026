package za.ca.cput.commerce.service;

/*
Author: isheanesu chowuraya (223182192)
2026/07/12
 */
import za.ca.cput.commerce.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review create(Review review);

    Optional<Review> read(String reviewId);

    Review update(Review review);

    boolean delete(String reviewId);

    List<Review> getAll();

    List<Review> getReviewsByProduct(String productId);

    List<Review> getReviewsByCustomer(String customerId);
}
