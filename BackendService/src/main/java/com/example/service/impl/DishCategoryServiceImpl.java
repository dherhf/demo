package com.example.service.impl;

import com.example.dto.DishCategoryDTO;
import com.example.dto.DishCategoryMapper;
import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
import com.example.repository.DishCategoryRepository;
import com.example.service.DishCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Autowired
    private DishCategoryMapper dishCategoryMapper;

    public List<DishCategoryDTO> getAllDishCategory() {
        List<DishCategory> dishCategory = dishCategoryRepository.findAll();
        return dishCategory.stream().map(dishCategoryMapper::toDTO).collect(Collectors.toList());
    }

    public DishCategoryDTO getDishCategoryById(Long id) {
        Optional<DishCategory> dishCategory = dishCategoryRepository.findById(id);
        if (dishCategory.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return dishCategoryMapper.toDTO(dishCategory.get());
    }

    public DishCategoryDTO createDishCategory(DishCategoryDTO dishCategoryDTO) {
        dishCategoryDTO.setId(null);
        dishCategoryDTO.setDishIds(null);
        DishCategory save = dishCategoryRepository.save(dishCategoryMapper.toEntity(dishCategoryDTO));
        return dishCategoryMapper.toDTO(save);

    }

    public DishCategoryDTO updateDishCategory(Long id, DishCategoryDTO dishCategoryDTO) {
        // 1. 校验URL ID与DTO ID是否一致
        if (!id.equals(dishCategoryDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        // 2. 检查ID是否存在
        if (!dishCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }

        DishCategory save = dishCategoryRepository.save(dishCategoryMapper.toEntity(dishCategoryDTO));
        return dishCategoryMapper.toDTO(save);
    }

    public boolean deleteDishCategory(Long id) {
        if (dishCategoryRepository.existsById(id)) {
            dishCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Dish> getDishByDishCategory(Long id) {
        Optional<DishCategory> dishCategory = dishCategoryRepository.findById(id);
        if (dishCategory.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return dishCategory.get().getDishes();
    }
}
