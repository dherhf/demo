package com.example.service;

import com.example.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomerById(int id);
    List<Customer> getAllCustomers();
    Customer addCustomer(Customer customer);
    boolean deleteCustomerById(int id);
    Customer updateCustomer(Customer customer);
}
