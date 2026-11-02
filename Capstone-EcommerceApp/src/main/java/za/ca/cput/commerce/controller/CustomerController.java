package za.ca.cput.commerce.controller;

/*
Author: 222709006 Qhama dyushu
19/07/26
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.factory.CustomerFactory;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        Customer newCustomer = CustomerFactory.createCustomer(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );

        if (newCustomer == null) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(service.create(newCustomer), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {

        Customer customer = service.read(customerId);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable String customerId,
            @RequestBody Customer customer) {

        Customer updatedCustomer = service.update(customerId, customer);

        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {

        boolean deleted = service.delete(customerId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {

        Optional<Customer> customer = service.findByEmail(email);

        return customer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(
            @RequestParam String name) {

        return ResponseEntity.ok(service.searchByName(name));
    }
}