package com.example.service.impl;

import com.example.dto.CustomerDTO;
import com.example.dto.CustomerMapper;
import com.example.model.customer.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return customerMapper.toDto(customer.get());
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> CustomerList = customerRepository.findAll();
        return CustomerList.stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(null);
        customerDTO.setOrderIds(null);
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer createCustomer = customerRepository.save(customer);
        return customerMapper.toDto(createCustomer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        // 1. 校验URL ID与DTO ID是否一致
        if (!id.equals(customerDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        // 2. 检查ID是否存在
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }

        customerDTO.setId(id);
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer updateCustomer = customerRepository.save(customer);
        return customerMapper.toDto(updateCustomer);
    }

    public boolean deleteCustomerById(int id) {
        if (customerRepository.existsById((long) id)) {
            customerRepository.deleteById((long) id);
            return true;
        }
        return false;
    }


}
