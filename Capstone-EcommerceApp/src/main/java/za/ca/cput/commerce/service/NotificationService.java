package za.ca.cput.commerce.service;

/*
Author: Tlangelani Chauke
12/07/2026
 */

import za.ca.cput.commerce.domain.Notification;

import java.util.List;

public interface NotificationService {

    Notification create(Notification notification);

    Notification read(String notificationId);

    Notification update(Notification notification);

    void delete(String notificationId);

    List<Notification> getAll();

    List<Notification> getNotificationsByCustomer(String customerId);
}
