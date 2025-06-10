package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dish_hint")
public class DishHint {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    // 提示类型
    private String hintType;
    // 提示文字
    private String hintText;
    // 提示优先权
    private String priority;
    // 提示所属菜品
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;
}
