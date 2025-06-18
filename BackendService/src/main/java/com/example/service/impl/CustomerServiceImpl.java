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

    /**
     * 根据客户 ID 获取客户信息。
     *
     * @param id 客户 ID
     * @return 客户数据传输对象
     * @throws EntityNotFoundException 如果指定 ID 的客户不存在
     */
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return customerMapper.toDto(customer.get());
    }


    /**
     * 获取所有客户信息。
     *
     * @return 客户数据传输对象列表
     */
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> CustomerList = customerRepository.findAll();
        return CustomerList.stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    /**
     * 创建新的客户信息。
     *
     * @param customerDTO 客户数据传输对象
     * @return 新创建的客户数据传输对象
     */
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(null);
        customerDTO.setOrderIds(null);
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer createCustomer = customerRepository.save(customer);
        return customerMapper.toDto(createCustomer);
    }

    /**
     * 根据客户 ID 更新客户信息。
     *
     * @param id 客户 ID
     * @param customerDTO 客户数据传输对象
     * @return 更新后的客户数据传输对象
     * @throws IllegalArgumentException 如果 URL 中的客户 ID 与 DTO 中的客户 ID 不一致
     * @throws EntityNotFoundException 如果指定 ID 的客户不存在
     */
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

    /**
     * 根据客户 ID 删除客户信息。
     *
     * @param id 要删除的客户的 ID，使用 int 类型
     * @return 如果成功删除客户信息返回 true，若客户 ID 不存在则返回 false
     */
    public boolean deleteCustomerById(int id) {
        if (customerRepository.existsById((long) id)) {
            customerRepository.deleteById((long) id);
            return true;
        }
        return false;
    }


}
