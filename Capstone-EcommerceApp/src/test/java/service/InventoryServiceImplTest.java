package service;

/*
Author: Plamedie 230082629
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.InventoryRepository;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.impl.InventoryServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.InventoryService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryServiceImplTest {

    @Autowired
    private InventoryService inventoryService;

    private static Inventory buildInventory() {
        Product product = new Product.Builder()
                .setProductId("P222")
                .build();

        return new Inventory.Builder()
                .setProduct(product)
                .setStockQuantity(100)
                .setWarehouseLocation("Cape Town Warehouse")
                .build();
    }

    private static final Inventory inventory = buildInventory();

    @Test
    @Order(1)
    void createInventory() {
        Inventory created = inventoryService.createInventory(inventory);

        assertNotNull(created);
        assertNotNull(created.getInventoryId());
        assertEquals(inventory.getStockQuantity(),
                created.getStockQuantity());
    }

    @Test
    @Order(2)
    void getInventoryById() {
        Inventory read = inventoryService.getInventoryById(
                inventory.getInventoryId());

        assertNotNull(read);
        assertEquals(inventory.getInventoryId(), read.getInventoryId());
    }

    @Test
    @Order(3)
    void updateInventory() {
        Inventory inventoryUpdated = new Inventory.Builder()
                .copy(inventory)
                .setStockQuantity(150)
                .setWarehouseLocation("Johannesburg Warehouse")
                .build();

        Inventory updated = inventoryService.updateInventory(
                inventory.getInventoryId(), inventoryUpdated);

        assertNotNull(updated);
        assertEquals(150, updated.getStockQuantity());
        assertEquals("Johannesburg Warehouse",
                updated.getWarehouseLocation());
    }

    @Test
    @Order(4)
    void getAllInventory() {
        List<Inventory> inventories = inventoryService.getAllInventory();

        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());
    }

    @Test
    @Order(5)
    void getInventoryByProductId() {
        Inventory found = inventoryService.getInventoryByProductId(
                inventory.getProduct().getProductId());

        assertNotNull(found);
        assertEquals(inventory.getProduct().getProductId(),
                found.getProduct().getProductId());
    }

    @Test
    @Order(6)
    void deleteInventory() {
        inventoryService.deleteInventory(inventory.getInventoryId());

        Inventory deleted = inventoryService.getInventoryByProductId(
                inventory.getProduct().getProductId());

        assertNull(deleted);
    }
}