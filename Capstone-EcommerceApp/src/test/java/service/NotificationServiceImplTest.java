package service;

/*
Author: Tlangelani Chauke
12/07/2026
 */
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.service.NotificationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificationServiceImplTest {

    @Autowired
    private NotificationService notificationService;

    private static Notification buildNotification() {
        Customer customer = new Customer.Builder()
                .setCustomerId("C222")
                .setName("John Doe")
                .setEmail("john@gmail.com")
                .setPhone("123456789")
                .build();

        return new Notification.Builder()
                .setCustomer(customer)
                .setMessage("Your order has been received.")
                .setStatus("UNREAD")
                .build();
    }

    private static final Notification notification = buildNotification();

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        Notification created = notificationService.create(notification);

        assertNotNull(created);
        assertNotNull(created.getNotificationId());
        assertEquals(notification.getMessage(), created.getMessage());
        assertEquals(notification.getStatus(), created.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        Notification read = notificationService.read(
                notification.getNotificationId());

        assertNotNull(read);
        assertEquals(notification.getNotificationId(),
                read.getNotificationId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        Notification notificationUpdated = new Notification.Builder()
                .copy(notification)
                .setMessage("Your order has been shipped.")
                .setStatus("READ")
                .build();

        Notification updated = notificationService.update(notificationUpdated);

        assertNotNull(updated);
        assertEquals(notification.getNotificationId(),
                updated.getNotificationId());
        assertEquals("Your order has been shipped(),",
                updated.getMessage());
        assertEquals("READ", updated.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        List<Notification> notifications = notificationService.getAll();

        assertNotNull(notifications);
        assertFalse(notifications.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getNotificationsByCustomer() {
        List<Notification> notifications =
                notificationService.getNotificationsByCustomer(
                        notification.getCustomer().getCustomerId());

        assertNotNull(notifications);
        assertFalse(notifications.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void delete() {
        notificationService.delete(notification.getNotificationId());

        Notification deleted = notificationService.read(
                notification.getNotificationId());

        assertNull(deleted);
    }
}
