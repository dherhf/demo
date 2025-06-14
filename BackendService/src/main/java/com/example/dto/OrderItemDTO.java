package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    @NotBlank
    private int quantity;
    @NotBlank
    private Long dishId;
    @NotBlank
    private Long orderId;
}
