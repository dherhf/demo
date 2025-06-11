package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishHintRequest {
    @NotBlank
    private String hintType;
    @NotBlank
    private String hintText;
    @NotBlank
    private String priority;
}
