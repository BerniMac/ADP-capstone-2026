/* 
  Order____.java
  Author: Joshua Jonathan Bird - 230444032
  Date: 22/03/2026
    */

package factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.OrderFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class OrderFactoryTest {

    private Customer sampleCustomer() {
        return new Customer.Builder()
                .setCustomerId("C123")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("0821234567")
                .build();
    }

    @Test
    void testCreateOrder() {
        Order order = OrderFactory.createOrder( sampleCustomer(), LocalDateTime.now(), 1500.00);   // was: "C123"
        assertNotNull(order);
        assertEquals("O001", order.getOrderId());
        assertEquals("C123", order.getCustomer().getCustomerId());   // was: order.getCustomerId()
        assertEquals(1500.00, order.getTotalAmount());
    }
}


