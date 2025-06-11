package com.example.service;

import com.example.model.dish.DishHint;

import java.util.Optional;


public interface DishHintService {
    Optional<DishHint> getDishHintById(long id);
    DishHint addDishHint(DishHint dishHint);
    void deleteDishHint(long id);
    DishHint updateDishHint(DishHint dishHint);
}
