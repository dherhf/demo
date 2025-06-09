package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dish_hint")
public class DishHint {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    // 提示类型
    @Column(name = "hint_type")
    private String hintType;
    // 提示文字
    @Column(name = "hint_text")
    private String hintText;
    // 提示优先权
    @Column(name = "priority")
    private String priority;
    // 提示所属菜品
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish")
    private Dish dish;
}
