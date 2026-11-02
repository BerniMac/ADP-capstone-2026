/* 
  Customer____.java
  Author: 222709006 Qhama dyushu
  Date: 22/03/2026
    */
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@JsonDeserialize(builder = Customer.Builder.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    // BCrypt hash of the password. The plain password is never stored or sent to clients.
    private String passwordHash;

    @JsonManagedReference("customer-address")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @JsonManagedReference("customer-orders")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @JsonManagedReference("customer-notification")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @JsonManagedReference("customer-review")
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Review> reviews = new ArrayList<>();

    protected Customer() {
    }

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.passwordHash = builder.passwordHash;

        this.addresses = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    //========================
    // Getters
    //========================

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    //========================
    // Helper Methods
    //========================

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public Order findOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    //========================
    // Builder
    //========================

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private String customerId;
        private String name;
        private String email;
        private String phone;
        private String passwordHash;

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        @JsonIgnore
        public Builder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder copy(Customer customer) {
            this.customerId = customer.customerId;
            this.name = customer.name;
            this.email = customer.email;
            this.phone = customer.phone;
            this.passwordHash = customer.passwordHash;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
