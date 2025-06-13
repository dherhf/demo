package com.example.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishCategoryDTO {
    private Long id;

    @Size(max = 100, message = "标签长度不能超过100个字符")
    private String tags;

    @NotBlank(message = "描述不能为空")
    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    // 只包含菜品ID列表，避免数据过大
    private List<Long> dishIds;
}
