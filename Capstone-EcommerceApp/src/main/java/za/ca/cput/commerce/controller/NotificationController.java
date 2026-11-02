package za.ca.cput.commerce.controller;

/*
Author: Tlangelani Chauke
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.service.CustomerService;
import za.ca.cput.commerce.service.NotificationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody Notification notification) {

        Notification created = service.create(notification);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable String id) {

        Notification notification = service.read(id);

        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable String id,
            @RequestBody Notification notification) {

        Notification existing = service.read(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Notification updated = new Notification.Builder()
                .copy(notification)
                .setNotificationId(id)
                .build();

        return ResponseEntity.ok(service.update(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String id) {

        Notification existing = service.read(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Notification>> getNotificationsByCustomer(
            @PathVariable String customerId) {

        return ResponseEntity.ok(
                service.getNotificationsByCustomer(customerId));
    }
}

