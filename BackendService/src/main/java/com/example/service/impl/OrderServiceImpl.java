package com.example.service.impl;

import com.example.dto.OrderDTO;
import com.example.dto.OrderMapper;
import com.example.model.order.Order;
import com.example.repository.OrderRepository;
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
    private OrderMapper orderMapper;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()){
            throw new EntityNotFoundException("not found");
        }
       return  orderMapper.toDTO(order.get());
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        orderDTO.setId(null);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        if (!id.equals(orderDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }
        if (!orderRepository.existsById(id)){
            throw new EntityNotFoundException("not found");
        }
        Order order = orderMapper.toEntity(orderDTO);
        Order save = orderRepository.save(order);
        return orderMapper.toDTO(save);

    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
