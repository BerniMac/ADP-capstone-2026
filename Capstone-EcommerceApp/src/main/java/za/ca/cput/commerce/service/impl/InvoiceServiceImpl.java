package za.ca.cput.commerce.service.impl;

/*
Author: Mogamad Jawaad Allie - 230472125
12/07/2026
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.repository.InvoiceRepository;
import za.ca.cput.commerce.service.InvoiceService;

import java.util.List;
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Invoice create(Invoice invoice) {
        return repository.save(invoice);
    }

    @Override
    public Invoice read(String invoiceId) {
        return repository.findById(invoiceId).orElse(null);
    }

    @Override
    public Invoice update(Invoice invoice) {
        if (repository.existsById(invoice.getInvoiceId())) {
            return repository.save(invoice);
        }
        return null;
    }

    @Override
    public boolean delete(String invoiceId) {
        if (repository.existsById(invoiceId)) {
            repository.deleteById(invoiceId);
            return true;
        }
        return false;
    }

    @Override
    public List<Invoice> getAll() {
        return repository.findAll();
    }

    @Override
    public Invoice getByOrderId(String orderId) {
        return repository.findByOrderOrderId(orderId).orElse(null);
    }
}