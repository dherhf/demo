package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Dish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    // 菜品名字
    private String name;
    // 菜品代码
    private String code;
    // 菜品描述
    private String description;
    // 菜品图片URL
    private String imageUrl;
    // 菜品类别
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_category_id")
    private DishCategory dishCategory;
    // 菜品提示
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DishHint> dishHints = new HashSet<>();
}
