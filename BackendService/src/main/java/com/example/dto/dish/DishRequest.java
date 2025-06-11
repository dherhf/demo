package com.example.dto.dish;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @NotNull(message = "菜品分类ID不能为空")
    @Positive(message = "菜品分类ID必须大于0")
    private Long dishCategoryId; // 必须指定分类ID
}
