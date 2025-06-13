package com.example.service;

import com.example.dto.DishCategoryDTO;
import com.example.model.dish.Dish;

import java.util.List;

public interface DishCategoryService {
    DishCategoryDTO getDishCategoryById(Long id);
    List<DishCategoryDTO> getAllDishCategory();
    DishCategoryDTO createDishCategory(DishCategoryDTO dishCategoryDTO);
    boolean deleteDishCategory(Long id);
    DishCategoryDTO updateDishCategory(Long id, DishCategoryDTO dishCategoryDTO);
    List<Dish> getDishByDishCategory(Long id);
}
