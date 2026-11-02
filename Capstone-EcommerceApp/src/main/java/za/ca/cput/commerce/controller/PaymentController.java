package za.ca.cput.commerce.controller;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Payment;

import za.ca.cput.commerce.service.CardService;
import za.ca.cput.commerce.service.OrderService;
import za.ca.cput.commerce.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")

public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return ResponseEntity.ok(service.create(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> update(
            @PathVariable String id,
            @RequestBody Payment payment) {

        return ResponseEntity.ok(service.update(id, payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<Payment>> getByCard(
            @PathVariable String cardId) {

        return ResponseEntity.ok(service.getByCard(cardId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getByOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(service.getByOrder(orderId));
    }
}

