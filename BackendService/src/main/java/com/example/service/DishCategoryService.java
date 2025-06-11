package com.example.service;

import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;

import java.util.List;
import java.util.Optional;

public interface DishCategoryService {
    Optional<DishCategory> findDishCategoryById(Long id);
    List<DishCategory> findAllDishCategory();
    DishCategory addDishCategory(DishCategory dishCategory);
    boolean deleteDishCategory(long id);
    DishCategory updateDishCategory(DishCategory dishCategory);
    List<Dish> findDishByDishCategory(Long id);
}
