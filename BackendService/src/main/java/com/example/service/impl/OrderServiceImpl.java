package com.example.service.impl;

import com.example.dto.OrderDTO;
import com.example.dto.OrderMapper;
import com.example.model.customer.Customer;
import com.example.model.desk.Desk;
import com.example.model.employee.Employee;
import com.example.model.order.Order;
import com.example.repository.*;
import com.example.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository  customerRepository;

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderMapper orderMapper;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return orderMapper.toDTO(order.get());
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        orderDTO.setId(null);
        return getOrderDTO(orderDTO);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        if (!id.equals(orderDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }
        return getOrderDTO(orderDTO);

    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private OrderDTO getOrderDTO(OrderDTO orderDTO) {
        Optional<Customer> customer = customerRepository.findById(orderDTO.getCustomerId());
        Optional<Desk> desk = deskRepository.findById(orderDTO.getDeskId());
        Optional<Employee> employee = employeeRepository.findById(orderDTO.getEmployeeId());
        if (customer.isEmpty() || desk.isEmpty() || employee.isEmpty()) {
            throw new IllegalArgumentException("bad request");
        }
        Order order = orderMapper.toEntity(orderDTO);
        order.setCustomer(customer.get());
        order.setDesk(desk.get());
        order.setEmployee(employee.get());
        Order save = orderRepository.save(order);
        return orderMapper.toDTO(save);
    }
}
