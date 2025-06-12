package com.example.service;

import com.example.model.dish.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {
    Optional<Dish> findDishById(Long id);
    List<Dish> findAllByDishCategoryId(int dishCategoryId);
    Dish addDish(Dish dish);
    Dish updateDish(Dish dish);
    void deleteDishById(Long id);
    List<Dish> getAllDishes();
}
