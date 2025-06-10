package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class DishCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    // 分类标签
    private String tags;
    // 描述
    private String description;
    // 菜品
    @OneToMany(mappedBy = "dishCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dish> dishes = new HashSet<>();

    public void addDish(Dish dish) {
        dishes.add(dish);
        dish.setDishCategory(this);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
        dish.setDishCategory(null);
    }

}
