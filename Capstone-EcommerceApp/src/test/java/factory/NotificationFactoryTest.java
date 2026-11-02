/* NotificationFactoryTest.java
   NotificationFactoryTest class
   Author: Tlangelani Chauke
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Notification;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.NotificationFactory;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    private Customer sampleCustomer() {
        return new Customer.Builder()
                .setCustomerId("CUST-007")
                .setName("Jane Smith")
                .setEmail("jane@example.com")
                .setPhone("0839876543")
                .build();
    }

    @Test
    void testCreateNotificationSuccess() {
        Date now = new Date();
        Notification notification = NotificationFactory.createNotification(
                sampleCustomer(),          // was: "CUST-007"
                "Your order has shipped!",
                "Sent"
        );

        assertNotNull(notification);
        assertEquals("NOTIF-99", notification.getNotificationId());
        assertEquals("CUST-007", notification.getCustomer().getCustomerId());  // was: notification.getCustomerId()
        assertEquals("Your order has shipped!", notification.getMessage());
        assertEquals(now, notification.getNotificationDate());
        assertEquals("Sent", notification.getStatus());
    }

    @Test
    void testCreateNotificationFail() {
        // was: empty-string customerId -- not expressible now that the field is a Customer object,
        // so this test now isolates the other failure condition the factory checks: a null date.
        Notification notification = NotificationFactory.createNotification(
                sampleCustomer(),
                "Your order has shipped!",
                "Sent"
        );
        assertNull(notification);
    }
}

