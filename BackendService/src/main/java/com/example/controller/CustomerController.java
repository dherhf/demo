package com.example.controller;

import com.example.dto.customer.CustomerRequest;
import com.example.dto.customer.CustomerResponse;
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

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 获取所有顾客
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        List<CustomerResponse> responses = allCustomers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 创建新顾客
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer newCustomer = new Customer();
        newCustomer.setName(customerRequest.getName());
        newCustomer.setAddress(customerRequest.getAddress());
        newCustomer.setPhone(customerRequest.getPhone());
        newCustomer.setCardID(customerRequest.getCardID());

        Customer savedCustomer = customerService.addCustomer(newCustomer);
        CustomerResponse response = convertToResponse(savedCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 根据ID获取顾客
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable int id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(c -> ResponseEntity.ok(convertToResponse(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 更新顾客信息
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable int id,
            @Valid @RequestBody CustomerRequest updateRequest) {
        Optional<Customer> customer = customerService.getCustomerById(id);

        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer customerToUpdate = customer.get();
        customerToUpdate.setName(updateRequest.getName());
        customerToUpdate.setAddress(updateRequest.getAddress());
        customerToUpdate.setPhone(updateRequest.getPhone());
        customerToUpdate.setCardID(updateRequest.getCardID());

        Customer updatedCustomer = customerService.updateCustomer(customerToUpdate);
        CustomerResponse response = convertToResponse(updatedCustomer);

        return ResponseEntity.ok(response);
    }

    // 删除顾客
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        boolean deleted = customerService.deleteCustomerById(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    // 辅助方法：将Customer转换为CustomerResponse
    private CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setName(customer.getName());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setCardID(customer.getCardID());
        return response;
    }
}
