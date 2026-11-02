package za.ca.cput.commerce.service.impl;

/*
Author: Tlangelani Chauke
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.repository.NotificationRepository;
import za.ca.cput.commerce.service.NotificationService;

import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification create(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public Notification read(String notificationId) {
        return repository.findById(notificationId).orElse(null);
    }

    @Override
    public Notification update(Notification notification) {

        if (notification == null || notification.getNotificationId() == null) {
            return null;
        }

        Notification existing =
                repository.findById(notification.getNotificationId()).orElse(null);

        if (existing == null) {
            return null;
        }

        return repository.save(notification);
    }

    @Override
    public void delete(String notificationId) {

        if (repository.existsById(notificationId)) {
            repository.deleteById(notificationId);
        }
    }

    @Override
    public List<Notification> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Notification> getNotificationsByCustomer(String customerId) {
        return repository.findByCustomerCustomerId(customerId);
    }
}
