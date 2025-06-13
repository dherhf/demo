package com.example.service.impl;

import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeMapper;
import com.example.model.employee.Employee;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("not found");
        }
        return employeeMapper.toDTO(employee.get());
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setId(null);
        employeeDTO.setOrderIds(null);
        Employee save = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        return employeeMapper.toDTO(save);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (!id.equals(employeeDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }
        Employee save = employeeRepository.save(employeeMapper.toEntity(employeeDTO));
        return employeeMapper.toDTO(save);
    }

    @Override
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
