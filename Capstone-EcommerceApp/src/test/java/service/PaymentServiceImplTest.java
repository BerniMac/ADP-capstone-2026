package service;



import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.ca.cput.commerce.domain.Payment;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.service.PaymentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    private static final String PAYMENT_ID = "PAY333";
    private static final String ORDER_ID = "ORD333";
    private static final String CARD_ID = "CARD333";

    private static Payment buildPayment() {
        za.ca.cput.commerce.domain.Order order =
                new za.ca.cput.commerce.domain.Order.Builder()
                        .setOrderId(ORDER_ID)
                        .build();

        Card card = new Card.Builder()
                .setCardId(CARD_ID)
                .build();

        return new Payment.Builder()
                .setPaymentId(PAYMENT_ID)
                .setOrder(order)
                .setPaymentAmount(500.00)
                .setPaymentMethod("Card")
                .setCard(card)
                .build();
    }

    private static final Payment payment = buildPayment();

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        Payment created = paymentService.create(payment);

        assertNotNull(created);
        assertEquals(PAYMENT_ID, created.getPaymentId());
        assertEquals(500.00, created.getPaymentAmount());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void getById() {
        Payment found = paymentService.getById(PAYMENT_ID);

        assertNotNull(found);
        assertEquals(PAYMENT_ID, found.getPaymentId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setPaymentAmount(750.00)
                .setPaymentMethod("Card")
                .build();

        Payment updated = paymentService.update(PAYMENT_ID, updatedPayment);

        assertNotNull(updated);
        assertEquals(750.00, updated.getPaymentAmount());
        assertEquals("Card", updated.getPaymentMethod());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        List<Payment> payments = paymentService.getAll();

        assertNotNull(payments);
        assertFalse(payments.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getByCard() {
        List<Payment> payments = paymentService.getByCard(CARD_ID);

        assertNotNull(payments);
        assertFalse(payments.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void getByOrder() {
        Payment found = paymentService.getByOrder(ORDER_ID);

        assertNotNull(found);
        assertEquals(ORDER_ID, found.getOrder().getOrderId());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void delete() {
        paymentService.delete(PAYMENT_ID);

        assertNull(paymentService.getById(PAYMENT_ID));
    }
}