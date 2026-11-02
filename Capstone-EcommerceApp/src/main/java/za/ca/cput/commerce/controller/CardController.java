package za.ca.cput.commerce.controller;

/*
author:isheanesu chowuraya(223182192)
19/07/2026
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.service.CardService;

import java.util.List;
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody Card card) {
        return ResponseEntity.ok(service.create(card));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> read(@PathVariable String id) {

        Card card = service.read(id);

        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(card);
    }

    @PutMapping
    public ResponseEntity<Card> update(@RequestBody Card card) {

        Card updated = service.update(card);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}