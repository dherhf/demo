package com.example.dto.dish;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishCategoryRequest {
    @NotBlank(message = "菜品分类标签不能为空")
    private String tags;
    @NotBlank(message = "菜品分类描述不能为空")
    private String description;
}
