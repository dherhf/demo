package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    @NotNull
    private int quantity;
    @NotNull
    private Long dishId;
    @NotNull
    private Long orderId;
}
