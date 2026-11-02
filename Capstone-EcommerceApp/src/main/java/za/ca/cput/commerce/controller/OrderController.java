package za.ca.cput.commerce.controller;

/*
Author: Joshua Jonathan Bird - 230444032
2026/07/19
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;

import za.ca.cput.commerce.service.CustomerService;
import za.ca.cput.commerce.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.ok(service.create(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> read(@PathVariable String id) {
        Order order = service.read(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable String id,
                                        @RequestBody Order order) {

        Order updatedOrder = new Order.Builder()
                .copy(order)
                .setOrderId(id)
                .build();

        Order updated = service.update(updatedOrder);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(
            @PathVariable String customerId) {

        return ResponseEntity.ok(service.getOrdersByCustomer(customerId));
    }
}