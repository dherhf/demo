package com.example.controller;

import com.example.dto.OrderItemDTO;
import com.example.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order-item")
@CrossOrigin(origins = "*")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        try {
            List<OrderItemDTO> responseDTO = orderItemService.getAllOrderItems();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemByOrder(@PathVariable("orderId") long orderId) {
        try {
            List<OrderItemDTO> responseDTO = orderItemService.getOrderItemsByOrderId(orderId);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@Valid @RequestBody OrderItemDTO requestDTO) {
        try {
            OrderItemDTO responseDTO = orderItemService.createOrderItem(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        try {
            OrderItemDTO responseDTO = orderItemService.getOrderItemById(id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItemDTO requestDTO) {
        try {
            OrderItemDTO responseDTO = orderItemService.updateOrderItem(id, requestDTO);
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
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        boolean deleted = orderItemService.deleteOrderItem(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
    }
}
