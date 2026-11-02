/* PaymentFactory.java
   This is the factory class to create Payment objects.
   Author: Mogamad Jawaad Allie - 230472125
   Date: 25 March 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Payment;
import java.util.UUID;

public class PaymentFactory {

    public static Payment createPayment(Order order,
                                        double paymentAmount,
                                        String paymentMethod,
                                        Card card) {
        if(paymentAmount <= 0){
            return null;
        }

        return new Payment.Builder()
                .setOrder(order)
                .setPaymentAmount(paymentAmount)
                .setPaymentMethod(paymentMethod)
                .setCard(card)
                .build();
    }
}
