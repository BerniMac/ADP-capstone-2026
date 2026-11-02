package za.ca.cput.commerce.service.impl;

/*
Author: Plamedie 230082629
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.InventoryRepository;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.InventoryService;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {

        Product product = productRepository.findById(
                        inventory.getProduct().getProductId())
                .orElse(null);

       // if (inventoryRepository.existsByProduct(product)) {
         //   throw new RuntimeException(
           //         "Inventory already exists for this product.");
        //}

        Inventory newInventory = new Inventory.Builder()
                .setProduct(product)
                .setStockQuantity(inventory.getStockQuantity())
                .setWarehouseLocation(inventory.getWarehouseLocation())
                .build();

        return inventoryRepository.save(newInventory);
    }

    @Override
    public Inventory getInventoryById(String inventoryId) {

        return inventoryRepository.findById(inventoryId)
                .orElse(null);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory updateInventory(String inventoryId,
                                     Inventory inventory) {

        Inventory existingInventory = inventoryRepository.findById(inventoryId)
                .orElse(null);

        Product product = productRepository.findById(
                        inventory.getProduct().getProductId())
                .orElse(null);

        Inventory updatedInventory =
                new Inventory.Builder()
                        .copy(existingInventory)
                        .setProduct(product)
                        .setStockQuantity(inventory.getStockQuantity())
                        .setWarehouseLocation(inventory.getWarehouseLocation())
                        .build();

        return inventoryRepository.save(updatedInventory);
    }

    @Override
    public void deleteInventory(String inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElse(null);

        inventoryRepository.delete(inventory);
    }

    @Override
    public Inventory getInventoryByProductId(String productId) {

        return inventoryRepository
                .findByProduct_ProductId(productId)
                .orElse(null);
    }
}