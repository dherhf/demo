package com.example.service;

import com.example.model.dish.DishCategory;
import com.example.repository.DishCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishCategoryService {
    private final DishCategoryRepository dishCategoryRepository;

    public DishCategoryService(DishCategoryRepository dishCategoryRepository) {
        this.dishCategoryRepository = dishCategoryRepository;
    }

    public Optional<DishCategory> findDishCategoryById(Long id) {
        return dishCategoryRepository.findById(id);
    }

    public List<DishCategory> findAllDishCategory() {
        return dishCategoryRepository.findAll();
    }

    public void addDishCategory(DishCategory dishCategory) {
        dishCategoryRepository.save(dishCategory);
    }

    public void deleteDishCategory(long id) {
        dishCategoryRepository.deleteById(id);
    }

    public void updateDishCategory(DishCategory dishCategory) {
        dishCategoryRepository.save(dishCategory);
    }
}
