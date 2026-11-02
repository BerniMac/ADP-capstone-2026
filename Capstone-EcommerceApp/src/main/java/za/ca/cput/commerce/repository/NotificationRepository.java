package za.ca.cput.commerce.repository;

/*
Author: Tlangelani Chauke

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByCustomerCustomerId(String customerId);

}
