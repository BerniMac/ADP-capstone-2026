package za.ca.cput.commerce.service.impl;

import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.repository.ReviewRepository;
import za.ca.cput.commerce.service.ReviewService;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review create(Review review) {
        return repository.save(review);
    }

    @Override
    public Optional<Review> read(String reviewId) {
        return repository.findById(reviewId);
    }

    @Override
    public Review update(Review review) {

        if (repository.existsById(review.getReviewId())) {
            return repository.save(review);
        }

        return null;
    }

    @Override
    public boolean delete(String reviewId) {

        if (repository.existsById(reviewId)) {
            repository.deleteById(reviewId);
            return true;
        }

        return false;
    }

    @Override
    public List<Review> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Review> getReviewsByProduct(String productId) {
        return repository.findByProductProductId(productId);
    }

    @Override
    public List<Review> getReviewsByCustomer(String customerId) {
        return repository.findByCustomerCustomerId(customerId);
    }
}
