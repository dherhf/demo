package com.example.controller;

import com.example.dto.customer.*;
import com.example.model.customer.Customer;
import com.example.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    // 获取所有顾客
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTO = allCustomers.stream().map(customerMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(customerDTO);
    }

    // 创建新顾客
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        Customer customer = customerMapper.toCreateEntity(customerRequest);
        customerService.addCustomer(customer);
        CustomerDTO customerDTO = customerMapper.toDto(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
    }

    // 根据ID获取顾客
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(value -> ResponseEntity.ok(customerMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 更新顾客信息
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable int id,
            @Valid @RequestBody UpdateCustomerRequest updateRequest) {
        if (updateRequest.getId() != null && !updateRequest.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Customer newCustomer = customerMapper.toUpdateEntity(updateRequest);
        Optional<Customer> OldCustomer = customerService.getCustomerById(id);
        if (OldCustomer.isPresent()) {
            customerService.updateCustomer(newCustomer);
            return ResponseEntity.ok(customerMapper.toDto(newCustomer));
        }
        return ResponseEntity.notFound().build();
    }

    // 删除顾客
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        boolean deleted = customerService.deleteCustomerById(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
