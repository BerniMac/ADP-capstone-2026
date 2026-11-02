package service;

/*
Author: Mogamad Jawaad Allie - 230472125
12/07/2026
*/


//mport org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.service.InvoiceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceServiceImplTest {

    @Autowired
    private InvoiceService invoiceService;

    private static Invoice buildInvoice() {
        Order order = new Order.Builder()
                .setOrderId("ORD222")
                .build();

        return new Invoice.Builder()
                .setOrder(order)
                .setTotalAmount(1000.00)
                .setTaxAmount(150.00)
                .setInvoiceStatus("PAID")
                .build();
    }

    private static final Invoice invoice = buildInvoice();

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        Invoice created = invoiceService.create(invoice);

        assertNotNull(created);
        assertNotNull(created.getInvoiceId());
        assertEquals(invoice.getTotalAmount(), created.getTotalAmount());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        Invoice read = invoiceService.read(invoice.getInvoiceId());

        assertNotNull(read);
        assertEquals(invoice.getInvoiceId(), read.getInvoiceId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        Invoice invoiceUpdated = new Invoice.Builder()
                .copy(invoice)
                .setTotalAmount(1200.00)
                .setTaxAmount(180.00)
                .setInvoiceStatus("UPDATED")
                .build();

        Invoice updated = invoiceService.update(invoiceUpdated);

        assertNotNull(updated);
        assertEquals(invoice.getInvoiceId(), updated.getInvoiceId());
        assertEquals(1200.00, updated.getTotalAmount());
        assertEquals(180.00, updated.getTaxAmount());
        assertEquals("UPDATED", updated.getInvoiceStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        List<Invoice> invoices = invoiceService.getAll();

        assertNotNull(invoices);
        assertFalse(invoices.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getByOrderId() {
        Invoice found = invoiceService.getByOrderId(
                invoice.getOrder().getOrderId());

        assertNotNull(found);
        assertEquals(invoice.getInvoiceId(), found.getInvoiceId());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void delete() {
        boolean deleted = invoiceService.delete(invoice.getInvoiceId());

        assertTrue(deleted);
        assertNull(invoiceService.read(invoice.getInvoiceId()));
    }
}