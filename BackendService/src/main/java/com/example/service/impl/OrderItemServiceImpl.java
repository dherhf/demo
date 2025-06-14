package com.example.service.impl;

import com.example.dto.OrderItemDTO;
import com.example.dto.OrderItemMapper;
import com.example.model.order.OrderItem;
import com.example.repository.OrderItemRepository;
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
    private OrderItemMapper orderItemMapper;

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
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
        OrderItem save = orderItemRepository.save(orderItemMapper.toEntity(orderItemDTO));
        return orderItemMapper.toDTO(save);
    }

    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        if (!id.equals(orderItemDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }
        if (!orderItemRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }
        OrderItem save = orderItemRepository.save(orderItemMapper.toEntity(orderItemDTO));
        return orderItemMapper.toDTO(save);
    }

    public boolean deleteOrderItem(Long id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
