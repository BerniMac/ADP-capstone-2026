/* 
  Order____.java
  Author: Joshua Jonathan Bird - 230444032
  Date: 22/03/2026
    */
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
@JsonDeserialize(builder = Order.Builder.class)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference("customer-order")
    private Customer customer;

    private LocalDateTime orderDate;
    private double totalAmount;

    @JsonManagedReference("order-orderItems")
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonManagedReference("payment-order")
    @OneToOne(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Payment payment;

    @JsonManagedReference("order-invoice")
    @OneToOne(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Invoice invoice;

    @JsonManagedReference("order-shipment")
    @OneToOne(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Shipment shipment;

    protected Order(){}

    private Order (Builder builder) {
        this.orderId = builder.orderId;
        this.customer = builder.customer;
        this.orderDate = builder.orderDate;
        this.totalAmount = builder.totalAmount;
    }

    //Getters
    public String getOrderId() {
        return orderId;

    }
    public Customer getCustomer() {
        return customer;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Shipment getShipment() {
        return shipment;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private  String orderId;
        private  Customer customer;
        private  LocalDateTime orderDate;
        private  double totalAmount;

        //Setters
        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }
        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }
        public Builder setOrderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }
        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder copy(Order order) {
            this.orderId = order.orderId;
            this.customer = order.customer;
            this.orderDate = order.orderDate;
            this.totalAmount = order.totalAmount;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}