/* Notification.java
   Notification POJO class
   Author: Tlangelani Chauke
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference("customer-notification")
    private Customer customer;
    @Column(nullable = false)
    private  String message;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime notificationDate;
    @Column(nullable = false)
    private  String status;

    protected Notification(){}

    private Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.customer = builder.customer;
        this.message = builder.message;
        this.notificationDate = builder.notificationDate;
        this.status = builder.status;
    }

    // Getters
    public String getNotificationId() {
        return notificationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public String getStatus() {
        return status;
    }

    // Builder Class
    public static class Builder {
        private String notificationId;
        private Customer customer;
        private String message;
        private LocalDateTime notificationDate;
        private String status;

        public Builder setNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNotificationDate(LocalDateTime notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Notification notification) {
            this.notificationId = notification.notificationId;
            this.customer = notification.customer;
            this.message = notification.message;
            this.notificationDate = notification.notificationDate;
            this.status = notification.status;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", message='" + message + '\'' +
                ", notificationDate=" + notificationDate +
                ", status='" + status + '\'' +
                '}';
    }
}
