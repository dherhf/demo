package com.example.dto.dish.category;


import lombok.Data;

import java.util.List;

@Data
public class DishCategoryDTO {
    private int id;
    private String tags;
    private String description;

    // 只包含菜品ID列表，避免数据过大
    private List<Integer> dishIds;
}
