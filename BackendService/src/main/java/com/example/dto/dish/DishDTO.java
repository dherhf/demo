package com.example.dto.dish;


import com.example.dto.dish.hint.DishHintDTO;
import lombok.Data;

import java.util.List;

@Data
public class DishDTO {
    private int id;
    private String name;
    // 菜品代码
    private String code;
    // 菜品描述
    private String description;
    // 菜品图片URL
    private String imageUrl;
    // 关联分类ID和基本信息
    private Integer categoryId;
    private String categoryName;

    // 提示信息列表
    private List<DishHintDTO> hints;
}
