package com.example.dto.dish.category;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateDishCategoryRequest {
    @NotBlank(message = "描述不能为空")
    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    @Size(max = 100, message = "标签长度不能超过100个字符")
    private String tags;
}
