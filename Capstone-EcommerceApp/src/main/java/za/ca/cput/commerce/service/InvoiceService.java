package za.ca.cput.commerce.service;

/*
Author: Mogamad Jawaad Allie - 230472125
12/07/2026
 */

import za.ca.cput.commerce.domain.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice create(Invoice invoice);

    Invoice read(String invoiceId);

    Invoice update(Invoice invoice);

    boolean delete(String invoiceId);

    List<Invoice> getAll();

    Invoice getByOrderId(String orderId);
}
