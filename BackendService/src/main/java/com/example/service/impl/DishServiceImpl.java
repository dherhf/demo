package com.example.service.impl;

import com.example.dto.DishDTO;
import com.example.dto.DishMapper;
import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
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
        return getDishDTO(dishDTO);
    }

    public DishDTO updateDish(Long id, DishDTO dishDTO) {
        if (!id.equals(dishDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        return getDishDTO(dishDTO);

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

    private DishDTO getDishDTO(DishDTO dishDTO) {
        Optional<DishCategory> dishCategory = dishCategoryRepository.findById(dishDTO.getCategoryId());
        if (dishCategory.isEmpty()) {
            throw new EntityNotFoundException("category not found");
        }
        Dish dish = dishMapper.toEntity(dishDTO);
        dish.setDishCategory(dishCategory.get());
        Dish save = dishRepository.save(dish);

        return dishMapper.toDTO(save);
    }
}
