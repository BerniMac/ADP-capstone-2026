/*
 * Product.java
 * Product model class
 * Author: Plamedie 230082629
 * Date: 04 March 2026
 */
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(builder = Product.Builder.class)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    private String productName;

    private String description;

    private double currentPrice;

    @JsonManagedReference
    @OneToOne(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Inventory inventory;


    @JsonManagedReference("product-review")
    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    protected Product() {
    }

    private Product(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.description = builder.description;
        this.currentPrice = builder.currentPrice;
    }

    //==========================
    // Getters
    //==========================

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    //==========================
    // Builder
    //==========================
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private String productId;
        private String productName;
        private String description;
        private double currentPrice;

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public Builder copy(Product product) {

            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.currentPrice = product.getCurrentPrice();

            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}

