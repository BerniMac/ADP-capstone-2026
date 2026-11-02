package za.ca.cput.commerce.controller;

/*
Author: isheanesu chowuraya (223182192)
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;

import za.ca.cput.commerce.service.CustomerService;
import za.ca.cput.commerce.service.ProductService;
import za.ca.cput.commerce.service.ReviewService;

import java.util.List;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return ResponseEntity.ok(service.create(review));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> read(@PathVariable String id) {
        return service.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable String id,
                                         @RequestBody Review review) {

        if (!id.equals(review.getReviewId())) {
            return ResponseEntity.badRequest().build();
        }

        Review updatedReview = service.update(review);

        if (updatedReview == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(service.getReviewsByProduct(productId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(service.getReviewsByCustomer(customerId));
    }
}


