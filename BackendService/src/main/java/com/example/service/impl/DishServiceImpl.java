package com.example.service.impl;

import com.example.dto.DishDTO;
import com.example.dto.DishMapper;
import com.example.model.dish.Dish;
import com.example.repository.DishCategoryRepository;
import com.example.repository.DishRepository;
import com.example.service.DishService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Autowired
    private DishMapper dishMapper;


    public List<DishDTO> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        return dishes.stream().map(d -> dishMapper.toDTO(d)).collect(Collectors.toList());
    }

    public DishDTO getDishById(Long id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return dishMapper.toDTO(dish.get());
    }

    public DishDTO createDish(DishDTO dishDTO) {
        dishDTO.setId(null);
        dishDTO.setHints(null);
        if (!dishCategoryRepository.existsById(dishDTO.getCategoryId())) {
            throw new EntityNotFoundException("category not found");
        }
        Dish dish = dishRepository.save(dishMapper.toEntity(dishDTO));
        return dishMapper.toDTO(dish);
    }

    public DishDTO updateDish(Long id, DishDTO dishDTO) {
        // 1. 校验URL ID与DTO ID是否一致
        if (!id.equals(dishDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        // 2. 检查ID是否存在
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }

        Dish dish = dishRepository.save(dishMapper.toEntity(dishDTO));
        return dishMapper.toDTO(dish);
    }

    public boolean deleteDishById(Long id) {
        if (dishRepository.existsById(id)) {
            dishRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DishDTO> findAllByDishCategoryId(int dishCategoryId) {
        return null;
    }
}
