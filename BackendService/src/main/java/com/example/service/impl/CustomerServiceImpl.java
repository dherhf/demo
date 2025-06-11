package com.example.service.impl;

import com.example.model.customer.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById((long) id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean deleteCustomerById(int id) {
        if (customerRepository.existsById((long) id)) {
            customerRepository.deleteById((long) id);
            return true;
        }
        return false;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
