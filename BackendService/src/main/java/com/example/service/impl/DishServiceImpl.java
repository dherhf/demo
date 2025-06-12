package com.example.service.impl;

import com.example.model.dish.Dish;
import com.example.repository.DishRepository;
import com.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository dishRepository;

    public Optional<Dish> findDishById(Long id) {
        return dishRepository.findById(id);
    }

    public  List<Dish> findAllByDishCategoryId(int dishCategoryId) {
        return  dishRepository.findAllByDishCategoryId(dishCategoryId);
    }

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish updateDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public void deleteDishById(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> getAllDishes() {
        return  dishRepository.findAll();
    }
}
