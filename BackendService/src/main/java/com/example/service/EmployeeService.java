package com.example.service;

import com.example.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createEmployee(EmployeeDTO  employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO  employeeDTO);
    boolean deleteEmployee(Long id);
}
