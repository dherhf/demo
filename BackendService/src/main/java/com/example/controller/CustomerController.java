package com.example.controller;

import com.example.dto.CustomerDTO;
import com.example.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        try {
            List<CustomerDTO> responseDTO = customerService.getAllCustomers();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 创建新顾客
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO requestDTO) {

        CustomerDTO responseDTO = customerService.createCustomer(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // 根据ID获取顾客
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        try {
            CustomerDTO responseDTO = customerService.getCustomerById(id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 更新顾客信息
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO requestDTO) {
        try {
            CustomerDTO responseDTO = customerService.updateCustomer(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            // 参数不合法（如ID不匹配）
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            // 资源不存在
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // 其他未预期异常
            return ResponseEntity.internalServerError().build();
        }
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
