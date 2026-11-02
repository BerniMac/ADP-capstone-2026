package za.ca.cput.commerce.repository;

/*
Author: Mogamad Jawaad Allie - 230472125

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByCardCardId(String cardId);

    Payment findByOrderOrderId(String orderId);
}
