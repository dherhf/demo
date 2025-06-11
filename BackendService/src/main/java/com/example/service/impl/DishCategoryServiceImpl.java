package com.example.service.impl;

import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
import com.example.repository.DishCategoryRepository;
import com.example.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    public Optional<DishCategory> findDishCategoryById(Long id) {
        return dishCategoryRepository.findById(id);
    }

    public List<DishCategory> findAllDishCategory() {
        return dishCategoryRepository.findAll();
    }

    public DishCategory addDishCategory(DishCategory dishCategory) {
        return dishCategoryRepository.save(dishCategory);
    }

    public boolean deleteDishCategory(long id) {
        if (dishCategoryRepository.existsById(id)) {
            dishCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public DishCategory updateDishCategory(DishCategory dishCategory) {
        return dishCategoryRepository.save(dishCategory);
    }

    public List<Dish> findDishByDishCategory(Long id) {
        Optional<DishCategory> dishCategory = dishCategoryRepository.findById(id);
        if (dishCategory.isPresent()) {
            return dishCategory.get().getDishes();
        }
        return List.of();
    }
}
