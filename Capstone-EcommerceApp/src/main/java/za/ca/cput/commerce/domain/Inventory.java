/* Inventory.java
   Inventory POJO class
   Author: Plamedie 230082629
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@JsonDeserialize(builder = Inventory.Builder.class)
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String inventoryId;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    //@Min(0)
    @Column(nullable = false)
    private int stockQuantity;

  //  @NotBlank
    @Column(nullable = false)
    private String warehouseLocation;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    protected Inventory() {
    }

    private Inventory(Builder builder) {
        this.inventoryId = builder.inventoryId;
        this.product = builder.product;
        this.stockQuantity = builder.stockQuantity;
        this.warehouseLocation = builder.warehouseLocation;
        this.lastUpdated = builder.lastUpdated;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private String inventoryId;
        private Product product;
        private int stockQuantity;
        private String warehouseLocation;
        private LocalDateTime lastUpdated;

        public Builder setInventoryId(String inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder setWarehouseLocation(String warehouseLocation) {
            this.warehouseLocation = warehouseLocation;
            return this;
        }

        public Builder setLastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder copy(Inventory inventory) {
            this.inventoryId = inventory.inventoryId;
            this.product = inventory.product;
            this.stockQuantity = inventory.stockQuantity;
            this.warehouseLocation = inventory.warehouseLocation;
            this.lastUpdated = inventory.lastUpdated;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId='" + inventoryId + '\'' +
                ", product=" + (product != null ? product.getProductId() : null) +
                ", stockQuantity=" + stockQuantity +
                ", warehouseLocation='" + warehouseLocation + '\'' +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}