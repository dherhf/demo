package com.example.service;

import com.example.dto.DishHintDTO;


public interface DishHintService {
    DishHintDTO getDishHintById(Long id);
    DishHintDTO createDishHint(DishHintDTO dishHintDTO);
    DishHintDTO updateDishHint(Long id, DishHintDTO dishHintDTO);
    boolean deleteDishHint(Long id);


}
