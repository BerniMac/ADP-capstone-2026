package za.ca.cput.commerce.controller;

/*
Author: Tlangelani Chauke
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Shipment;

import za.ca.cput.commerce.service.OrderService;
import za.ca.cput.commerce.service.ShipmentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Shipment> create(@RequestBody Shipment shipment) {
        return ResponseEntity.ok(service.create(shipment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> read(@PathVariable String id) {
        Shipment shipment = service.read(id);

        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(shipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> update(@PathVariable String id,
                                           @RequestBody Shipment shipment) {

        Shipment existingShipment = service.read(id);

        if (existingShipment == null) {
            return ResponseEntity.notFound().build();
        }

        Shipment updatedShipment = new Shipment.Builder()
                .copy(shipment)
                .setShipmentId(id)
                .build();

        return ResponseEntity.ok(service.update(updatedShipment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        Shipment shipment = service.read(id);

        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipment> getByOrderId(@PathVariable String orderId) {

        Shipment shipment = service.findByOrderId(orderId);

        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(shipment);
    }
}