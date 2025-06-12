package com.example.dto.dish.hint;

import lombok.Data;

@Data
public class DishHintDTO {
    private Integer id;
    private String hintText;
    private String hintType;
    private String priority;
    private Integer dishId; // 关联菜品ID
}
