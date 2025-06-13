package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishHintDTO {
    private Long id;
    @NotBlank
    private String hintText;
    @NotBlank
    private String hintType;
    @NotBlank
    private String priority;
    @NotBlank
    private Long dishId; // 关联菜品ID
}
