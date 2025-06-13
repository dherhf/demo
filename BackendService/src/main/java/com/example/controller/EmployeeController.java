package com.example.controller;

import com.example.dto.CustomerDTO;
import com.example.dto.EmployeeDTO;
import com.example.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllCustomers() {
        try {
            List<EmployeeDTO> responseDTO =  employeeService.getAllEmployees();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createCustomer(@Valid @RequestBody EmployeeDTO requestDTO) {
        try {
            EmployeeDTO responseDTO = employeeService.createEmployee(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getCustomerById(@PathVariable Long id) {
        try {
            EmployeeDTO responseDTO = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody EmployeeDTO requestDTO) {
        try {
            EmployeeDTO responseDTO = employeeService.updateEmployee(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.internalServerError().build();
    }

}
