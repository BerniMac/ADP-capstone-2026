/* NotificationFactory.java
   NotificationFactory class
   Author: Tlangelani Chauke
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Notification;

import java.time.LocalDateTime;
import java.util.Date;

public class NotificationFactory {

    public static Notification createNotification(Customer customer,
                                                  String message,
                                                  String status) {

        if (customer == null) {
            return null;
        }

        if (message == null || message.isBlank()) {
            return null;
        }

        if (status == null || status.isBlank()) {
            return null;
        }

        return new Notification.Builder()
                .setCustomer(customer)
                .setMessage(message)
                .setStatus(status)
                .build();
    }
}
