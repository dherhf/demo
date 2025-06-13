package com.example.service;

import com.example.dto.DishDTO;

import java.util.List;


public interface DishService {
    List<DishDTO> getAllDishes();
    DishDTO getDishById(Long id);
    DishDTO createDish(DishDTO DishDTO);
    DishDTO updateDish(Long id, DishDTO DishDTO);
    boolean deleteDishById(Long id);
    List<DishDTO> findAllByDishCategoryId(int dishCategoryId);
}
