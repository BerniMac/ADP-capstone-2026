package za.ca.cput.commerce.service.impl;

/*
Author: Mogamad Jawaad Allie - 230472125
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Payment;
import za.ca.cput.commerce.repository.PaymentRepository;
import za.ca.cput.commerce.service.PaymentService;

import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment create(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public Payment update(String paymentId, Payment payment) {

        Payment existing = getById(paymentId);

        Payment updated = new Payment.Builder()
                .copy(existing)
                .setOrder(payment.getOrder())
                .setPaymentAmount(payment.getPaymentAmount())
                .setPaymentMethod(payment.getPaymentMethod())
                .setCard(payment.getCard())
                .build();

        return repository.save(updated);
    }

    @Override
    public Payment getById(String paymentId) {
        return repository.findById(paymentId)
                .orElse(null);
    }

    @Override
    public List<Payment> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String paymentId) {
        repository.deleteById(paymentId);
    }

    @Override
    public List<Payment> getByCard(String cardId) {
        return repository.findByCardCardId(cardId);
    }

    @Override
    public Payment getByOrder(String orderId) {
        return repository.findByOrderOrderId(orderId);
    }
}