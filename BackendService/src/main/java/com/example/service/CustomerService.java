package com.example.service;

import com.example.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO createCustomer(CustomerDTO customer);
    boolean deleteCustomerById(int id);
    CustomerDTO updateCustomer(Long id, CustomerDTO customer);
}
