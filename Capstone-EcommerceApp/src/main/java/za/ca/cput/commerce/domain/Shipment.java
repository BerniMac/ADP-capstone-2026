/*
 * Shipment.java
 * Shipment model class
 * Author: Tlangelani Chauke
 * Date:22march 2026
 */
package za.ca.cput.commerce.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
@JsonDeserialize(builder = Shipment.Builder.class)
@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String shipmentId;
    private String address;
    private LocalDateTime shipmentDate;
    private LocalDateTime deliveryDate;
    private String status;
    @JsonBackReference("shipment-orders")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    protected Shipment(){}

    // Private constructor
    private Shipment(Builder builder) {
        this.shipmentId = builder.shipmentId;
        this.address = builder.address;
        this.shipmentDate = builder.shipmentDate;
        this.deliveryDate = builder.deliveryDate;
        this.status = builder.status;
        this.order = builder.order;
    }

    // Getters
    public String getShipmentId() {
        return shipmentId;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Order getOrder() {
        return order;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String shipmentId;
        private String address;
        private LocalDateTime shipmentDate;
        private LocalDateTime deliveryDate;
        private String status;
        private Order order;

        public Builder setShipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setShipmentDate(LocalDateTime shipmentDate) {
            this.shipmentDate = shipmentDate;
            return this;
        }

        public Builder setDeliveryDate(LocalDateTime deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder copy(Shipment shipment) {
            this.shipmentId = shipment.shipmentId;
            this.address = shipment.address;
            this.shipmentDate = shipment.shipmentDate;
            this.deliveryDate = shipment.deliveryDate;
            this.status = shipment.status;
            this.order = shipment.order;
            return this;
        }

        public Shipment build() {
            return new Shipment(this);
        }
    }
}
