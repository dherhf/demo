package com.example.dto.dish;


import lombok.Data;

@Data
public class DishResponse {
    private int id;
    private String name;
    // 菜品代码
    private String code;
    // 菜品描述
    private String description;
    // 菜品图片URL
    private String imageUrl;
    private DishCategoryResponse dishCategory;
}
