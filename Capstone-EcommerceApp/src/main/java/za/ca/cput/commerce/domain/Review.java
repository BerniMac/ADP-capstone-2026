/* Review.java
   Review POJO class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@JsonDeserialize(builder = Review.Builder.class)
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String reviewId;

    @JsonBackReference("customer-review")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonBackReference("product-review")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private int rating;
    private  String comment;
    private LocalDateTime reviewDate;

    protected Review(){}

    private Review(Builder builder) {
        this.reviewId = builder.reviewId;
        this.customer = builder.customer;
        this.product = builder.product;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.reviewDate = builder.reviewDate;
    }

    // Getters
    public String getReviewId() {
        return reviewId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String reviewId;
        private Customer customer;
        private Product product;
        private int rating;
        private String comment;
        @CreationTimestamp
        private LocalDateTime reviewDate;

        public Builder setReviewId(String reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

//        public Builder setReviewDate(LocalDateTime reviewDate) {
//            this.reviewDate = reviewDate;
//            return this;
//        }

        public Builder copy(Review review) {
            this.reviewId = review.reviewId;
            this.customer = review.customer;
            this.product = review.product;
            this.rating = review.rating;
            this.comment = review.comment;
            this.reviewDate = review.reviewDate;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", product=" + (product != null ? product.getProductId() : null) +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                '}';
    }
}