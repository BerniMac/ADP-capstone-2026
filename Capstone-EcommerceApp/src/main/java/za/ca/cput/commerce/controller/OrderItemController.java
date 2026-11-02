package za.ca.cput.commerce.controller;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;

import za.ca.cput.commerce.service.OrderItemService;
import za.ca.cput.commerce.service.OrderService;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;
@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderItem> create(@RequestBody OrderItem orderItem) {
        OrderItem created = service.create(orderItem);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> read(@PathVariable String id) {
        OrderItem orderItem = service.read(id);

        if (orderItem == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(@PathVariable String id,
                                            @RequestBody OrderItem orderItem) {

        OrderItem updated = service.update(id, orderItem);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        OrderItem existing = service.read(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(service.getByOrder(orderId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItem>> getByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(service.getByProduct(productId));
    }
}