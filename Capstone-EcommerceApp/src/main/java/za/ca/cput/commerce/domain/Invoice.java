/* Invoice.java
   Invoice POJO class
   Author: Mogamad Jawaad Allie - 230472125
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@JsonDeserialize(builder = Invoice.Builder.class)
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String invoiceId;
    @JsonBackReference("invoice-orders")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;
    @CreationTimestamp
    private LocalDateTime invoiceDate;
    @Column(nullable = false)
    private  double totalAmount;
    @Column(nullable = false)
    private  double taxAmount;
    @Column(nullable = false)
    private  String invoiceStatus;

    protected Invoice(){}

    private Invoice(Builder builder) {
        this.invoiceId = builder.invoiceId;
        this.order = builder.order;
        this.invoiceDate = builder.invoiceDate;
        this.totalAmount = builder.totalAmount;
        this.taxAmount = builder.taxAmount;
        this.invoiceStatus = builder.invoiceStatus;
    }

    // Getters
    public String getInvoiceId() {
        return invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String invoiceId;
        private Order order;
        private LocalDateTime invoiceDate;
        private double totalAmount;
        private double taxAmount;
        private String invoiceStatus;

        public Builder setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setInvoiceDate(LocalDateTime invoiceDate) {
            this.invoiceDate = invoiceDate;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder setInvoiceStatus(String invoiceStatus) {
            this.invoiceStatus = invoiceStatus;
            return this;
        }

        public Builder copy(Invoice invoice) {
            this.invoiceId = invoice.invoiceId;
            this.order = invoice.order;
            this.invoiceDate = invoice.invoiceDate;
            this.totalAmount = invoice.totalAmount;
            this.taxAmount = invoice.taxAmount;
            this.invoiceStatus = invoice.invoiceStatus;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", order=" + (order != null ? order.getOrderId() : null) +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", totalAmount=" + totalAmount +
                ", taxAmount=" + taxAmount +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                '}';
    }
}