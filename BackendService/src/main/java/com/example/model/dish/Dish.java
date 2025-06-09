package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "dish")
public class Dish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private int id;
    // 菜品名字
    @Column(name = "name")
    private String name;
    // 菜品代码
    @Column(name = "code")
    private String code;
    // 菜品描述
    @Column(name = "description")
    private String description;
    // 菜品图片URL
    @Column(name = "image_url")
    private String imageUrl;
    // 菜品类别
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_category")
    private DishCategory category;
    // 菜品提示
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DishHint> hints = new HashSet<>();
}
