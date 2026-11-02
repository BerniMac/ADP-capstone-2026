package za.ca.cput.commerce.service.impl;

/*
Author: 222709006 Qhama dyushu
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.repository.CustomerRepository;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Customer read(String customerId) {
        return repository.findById(customerId).orElse(null);
    }

    @Override
    public Customer update(String customerId, Customer customer) {

        Customer existingCustomer = read(customerId);

        if (existingCustomer == null) {
            return null;
        }

        Customer updatedCustomer = new Customer.Builder()
                .copy(existingCustomer)
                .setName(customer.getName())
                .setEmail(customer.getEmail())
                .setPhone(customer.getPhone())
                .build();

        return repository.save(updatedCustomer);
    }

    @Override
    public boolean delete(String customerId) {

        if (!repository.existsById(customerId)) {
            return false;
        }

        repository.deleteById(customerId);
        return true;
    }

    @Override
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<Customer> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}

