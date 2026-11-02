package za.ca.cput.commerce.controller;

/*
Author: Plamedie 230082629
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;

import za.ca.cput.commerce.service.InventoryService;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @GetMapping("/{inventoryId}")
    public Inventory getInventoryById(@PathVariable String inventoryId) {
        return inventoryService.getInventoryById(inventoryId);
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PutMapping("/{inventoryId}")
    public Inventory updateInventory(@PathVariable String inventoryId,
                                     @RequestBody Inventory inventory) {
        return inventoryService.updateInventory(inventoryId, inventory);
    }

    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable String inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }

    @GetMapping("/product/{productId}")
    public Inventory getInventoryByProduct(@PathVariable String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }
}

