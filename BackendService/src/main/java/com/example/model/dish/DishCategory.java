package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "dish_category")
public class DishCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private int id;
    // 分类标签
    @Column(name = "tags")
    private String tags;
    // 描述
    @Column(name = "description")
    private String description;
    // 菜品
    @OneToMany(mappedBy = "dish_category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dish> dishes = new HashSet<>();

    public void addDish(Dish dish) {
        dishes.add(dish);
        dish.setCategory(this);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
        dish.setCategory(null);
    }

}
