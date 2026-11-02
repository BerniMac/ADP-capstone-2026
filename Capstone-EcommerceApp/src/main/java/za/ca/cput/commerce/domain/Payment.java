/* Payment.java
   This is the domain class for Payment using the Builder pattern.
   Author: Mogamad Jawaad Allie - 230472125
   Date: 25 March 2026
   Added again due to branching issues with IntelliJ
*/
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonDeserialize(builder = Payment.Builder.class)
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @JsonBackReference("payment-order")
    private Order order;

    private  double paymentAmount;
    @CreationTimestamp
    private LocalDateTime paymentDate;
    private  String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable =false)
    @JsonBackReference("card-payment")
    private Card card;

    protected Payment(){}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.order = builder.order;
        this.paymentAmount = builder.paymentAmount;
        this.paymentDate = builder.paymentDate;
        this.paymentMethod = builder.paymentMethod;
        this.card = builder.card;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Card getCard() {
        return card;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String paymentId;
        private Order order;
        private double paymentAmount;
        private LocalDateTime paymentDate;
        private String paymentMethod;
        private Card card;

        public Builder setPaymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setPaymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder setCard(Card card) {
            this.card = card;
            return this;
        }

        // Copy method for updating
        public Builder copy(Payment payment) {
            this.paymentId = payment.paymentId;
            this.order = payment.order;
            this.paymentAmount = payment.paymentAmount;
            this.paymentDate = payment.paymentDate;
            this.paymentMethod = payment.paymentMethod;
            this.card = payment.card;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", order=" + (order != null ? order.getOrderId() : null) +
                ", paymentAmount=" + paymentAmount +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", card=" + (card != null ? card.getCardId() : null) +
                '}';
    }
}