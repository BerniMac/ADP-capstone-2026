/* InvoiceFactory.java
   InvoiceFactory class
   Author: Mogamad Jawaad Allie - 230472125
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.domain.Order;

import java.time.LocalDateTime;

public class InvoiceFactory {

    public static Invoice createInvoice(Order order,
                                        double totalAmount,
                                        double taxAmount,
                                        String invoiceStatus) {

        if (order == null) {
            return null;
        }

        if (totalAmount < 0 || taxAmount < 0) {
            return null;
        }

        if (invoiceStatus == null || invoiceStatus.isBlank()) {
            return null;
        }

        return new Invoice.Builder()
                .setOrder(order)
                .setTotalAmount(totalAmount)
                .setTaxAmount(taxAmount)
                .setInvoiceStatus(invoiceStatus)
                .build();
    }
}
