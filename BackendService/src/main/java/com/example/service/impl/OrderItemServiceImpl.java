package com.example.service.impl;

import com.example.dto.OrderItemDTO;
import com.example.dto.OrderItemMapper;
import com.example.model.dish.Dish;
import com.example.model.order.Order;
import com.example.model.order.OrderItem;
import com.example.repository.DishRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList.stream().map(orderItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(Math.toIntExact(orderId));
        return orderItemList.stream().map(orderItemMapper::toDTO).collect(Collectors.toList());
    }

    public OrderItemDTO getOrderItemById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return orderItemMapper.toDTO(orderItem.get());
    }

    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        orderItemDTO.setId(null);
        return getOrderItemDTO(orderItemDTO);
    }

    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        if (!id.equals(orderItemDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }
        return getOrderItemDTO(orderItemDTO);
    }

    public boolean deleteOrderItem(Long id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private OrderItemDTO getOrderItemDTO(OrderItemDTO orderItemDTO) {
        Optional<Order> order = orderRepository.findById(orderItemDTO.getOrderId());
        Optional<Dish> dish = dishRepository.findById(orderItemDTO.getDishId());
        if (order.isEmpty() || dish.isEmpty()) {
            throw new IllegalArgumentException("bad request");
        }
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem.setDish(dish.get());
        orderItem.setOrder(order.get());
        OrderItem save = orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(save);
    }
}
