package za.ca.cput.commerce.service;

/*
Author: Mogamad Jawaad Allie - 230472125
12/07/2026
 */
import za.ca.cput.commerce.domain.Payment;
import java.util.List;

public interface PaymentService {

    Payment create(Payment payment);

    Payment update(String paymentId, Payment payment);

    Payment getById(String paymentId);

    List<Payment> getAll();

    void delete(String paymentId);

    List<Payment> getByCard(String cardId);

    Payment getByOrder(String orderId);
}
