package com.example.service;

import com.example.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDTO> getAllOrderItems();

    List<OrderItemDTO> getOrderItemsByOrderId(Long orderId);

    OrderItemDTO getOrderItemById(Long id);

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

    OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);

    boolean deleteOrderItem(Long id);
}
