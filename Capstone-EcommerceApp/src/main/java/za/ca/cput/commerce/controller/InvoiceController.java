package za.ca.cput.commerce.controller;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.domain.Order;

import za.ca.cput.commerce.service.InvoiceService;
import za.ca.cput.commerce.service.OrderService;

import java.util.List;
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        Invoice createdInvoice = service.create(invoice);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> read(@PathVariable String invoiceId) {
        Invoice invoice = service.read(invoiceId);

        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<Invoice> update(@PathVariable String invoiceId,
                                          @RequestBody Invoice invoice) {

        if (service.read(invoiceId) == null) {
            return ResponseEntity.notFound().build();
        }

        Invoice updatedInvoice = new Invoice.Builder()
                .copy(invoice)
                .setInvoiceId(invoiceId)
                .build();

        return ResponseEntity.ok(service.update(updatedInvoice));
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> delete(@PathVariable String invoiceId) {

        if (service.delete(invoiceId)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Invoice> getByOrderId(@PathVariable String orderId) {

        Invoice invoice = service.getByOrderId(orderId);

        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invoice);
    }
}
