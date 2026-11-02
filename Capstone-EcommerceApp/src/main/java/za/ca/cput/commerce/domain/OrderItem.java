/* OrderItem.java
   OrderItem POJO class
   Author: Joshua Jonathan Bird - 230444032
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonDeserialize(builder = OrderItem.Builder.class)
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String orderItemId;

    @JsonBackReference("order-orderItem")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    // Product details (name, price) must be visible to the UI, so the product is serialised.
    // Its collections are ignored to avoid loops and lazy-loading problems.
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reviews", "inventory"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private  int quantity;
    private  double priceAtPurchase;

    private OrderItem(){}

    private OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.order = builder.order;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.priceAtPurchase = builder.priceAtPurchase;
    }

    // Getters
    public String getOrderItemId() {
        return orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    @Transient
    public double getSubtotal() {
        return quantity * priceAtPurchase;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String orderItemId;
        private Order order;
        private Product product;
        private int quantity;
        private double priceAtPurchase;

        public Builder setOrderItemId(String orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setPriceAtPurchase(double priceAtPurchase) {
            this.priceAtPurchase = priceAtPurchase;
            return this;
        }

        public Builder copy(OrderItem orderItem) {
            this.orderItemId = orderItem.orderItemId;
            this.order = orderItem.order;
            this.product = orderItem.product;
            this.quantity = orderItem.quantity;
            this.priceAtPurchase = orderItem.priceAtPurchase;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId='" + orderItemId + '\'' +
                ", order=" + (order != null ? order.getOrderId() : null) +
                ", product=" + (product != null ? product.getProductId() : null) +
                ", quantity=" + quantity +
                ", priceAtPurchase=" + priceAtPurchase +
                '}';
    }
}