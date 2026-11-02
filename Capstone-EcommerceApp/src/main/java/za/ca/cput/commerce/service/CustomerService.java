package za.ca.cput.commerce.service;

/*
Author: 222709006 Qhama dyushu
12/07/2026
 */

import za.ca.cput.commerce.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer create(Customer customer);

    Customer read(String customerId);

    Customer update(String customerId, Customer customer);

    boolean delete(String customerId);

    List<Customer> getAll();

    Optional<Customer> findByEmail(String email);

    List<Customer> searchByName(String name);
}
