package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.CustomerRequest;
import com.example.model.customer.Customer;
import com.example.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("get-all")
    public ResponseEntity<ApiResponse<List<Customer>>> getAll() {
        try {
            List<Customer> allCustomers = customerService.getAllCustomers();
            return ResponseEntity.ok(new ApiResponse<>(true, "获取所有顾客成功", allCustomers));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "服务器错误", null));
        }
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<Customer>> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        try {
            Customer newCustomer = new Customer();
            newCustomer.setName(customerRequest.getName());
            newCustomer.setAddress(customerRequest.getAddress());
            newCustomer.setPhone(customerRequest.getPhone());
            newCustomer.setCardID(customerRequest.getCardID());
            System.out.println(newCustomer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "添加顾客成功", customerService.addCustomer(newCustomer))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "服务器错误", null));
        }
    }

    // 删除顾客
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable int id) {
        try {
            // 检查顾客是否存在
            Optional<Customer> customer = customerService.getCustomerById(id);
            if (customer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "顾客不存在，删除失败", null));
            }

            // 删除顾客
            customerService.deleteCustomer(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "删除顾客成功", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "服务器错误", null));
        }
    }

    // 修改顾客
    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(
            @PathVariable int id,
            @Valid @RequestBody CustomerRequest updateRequest) {
        try {
            // 检查顾客是否存在
            Optional<Customer> oldCustomer = customerService.getCustomerById(id);
            if (oldCustomer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "顾客不存在，更新失败", null));
            }

            // 更新顾客信息
            Customer newcustomer = oldCustomer.get();
            newcustomer.setName(updateRequest.getName());
            newcustomer.setAddress(updateRequest.getAddress());
            newcustomer.setPhone(updateRequest.getPhone());
            newcustomer.setCardID(updateRequest.getCardID());

            // 保存更新后的顾客信息
            Customer updatedCustomer = customerService.updateCustomer(newcustomer);
            return ResponseEntity.ok(new ApiResponse<>(true, "更新顾客信息成功", updatedCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "服务器错误", null));
        }
    }


}
