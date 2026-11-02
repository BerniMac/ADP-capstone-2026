/* PaymentFactoryTest.java
   This is the test class for PaymentFactory.
   Author: Mogamad Jawaad Allie - 230472125
   Date: 25 March 2026
*/
package factory;

import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Payment;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.PaymentFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class PaymentFactoryTest {

    private Order sampleOrder() {
        return new Order.Builder()
                .setOrderId("ORD-123")
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(500.00)
                .build();
    }

    private Card SampleCard(){
        return new Card.Builder()
                .setCardId("id434")
                .setCardNumber("99099452")
                .setCardType("standard")
                .setCardHolderName("john")
                .setCardExpiry("24/04/2028")
                .setCardCVV("9099343")
                .build();
    }
    @Test
    void testCreatePayment() {
        Payment payment = PaymentFactory.createPayment(sampleOrder(), 500.00, "2026-03-25", SampleCard()); // was: "ORD-123"

        // Assertions
        assertNotNull(payment);
        assertNotNull(payment.getPaymentId()); // ID should be generated
        assertEquals("ORD-123", payment.getOrder().getOrderId());  // was: payment.getOrderId()
        assertEquals(500.00, payment.getPaymentAmount());
        assertEquals("Credit Card", payment.getPaymentMethod());

        System.out.println("Payment Created: " + payment.toString());
    }

    @Test
    void testCreatePaymentWithFail() {
        // Test with invalid amount
        Payment payment = PaymentFactory.createPayment(sampleOrder(), -10.00, "2026-03-25", SampleCard());
        assertNull(payment);
    }
}

