package com.example.controller;

import com.example.dto.dish.category.CreateDishCategoryRequest;
import com.example.dto.dish.category.DishCategoryDTO;
import com.example.dto.dish.category.DishCategoryMapper;
import com.example.dto.dish.category.UpdateDishCategoryRequest;
import com.example.model.dish.DishCategory;
import com.example.service.DishCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/dish-category")
@CrossOrigin(origins = "*")
public class DishCategoryController {
    private final DishCategoryService dishCategoryService;
    private final DishCategoryMapper dishCategoryMapper;

    public DishCategoryController(DishCategoryService dishCategoryService, DishCategoryMapper dishCategoryMapper) {
        this.dishCategoryService = dishCategoryService;
        this.dishCategoryMapper = dishCategoryMapper;
    }

    // 获取所有菜品分类
    @GetMapping
    public ResponseEntity<List<DishCategoryDTO>> getAllDishCategories() {
        List<DishCategory> categories = dishCategoryService.findAllDishCategory();

        List<DishCategoryDTO> dishCategoryDTOList = categories.stream()
                .map(dishCategoryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dishCategoryDTOList);
    }

    // 添加菜品分类
    @PostMapping
    public ResponseEntity<DishCategoryDTO> addDishCategory(@Valid @RequestBody CreateDishCategoryRequest category) {
        try {
            DishCategory dishCategory = dishCategoryMapper.toEntity(category);
            DishCategory addedDishCategory = dishCategoryService.addDishCategory(dishCategory);
            DishCategoryDTO dishCategoryDTO = dishCategoryMapper.toDTO(addedDishCategory);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(dishCategoryDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 根据ID获取菜品分类
    @GetMapping("/{id}")
    public ResponseEntity<DishCategoryDTO> getDishCategoryById(@PathVariable Long id) {
        Optional<DishCategory> category = dishCategoryService.findDishCategoryById(id);

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DishCategoryDTO dishCategoryDTO = dishCategoryMapper.toDTO(category.get());
        return ResponseEntity.ok(dishCategoryDTO);
    }



    // 更新菜品分类
    @PutMapping("/{id}")
    public ResponseEntity<DishCategoryDTO> updateDishCategory(
            @PathVariable long id,
            @Valid @RequestBody UpdateDishCategoryRequest category) {
        try {
            Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(id);
            if (dishCategory.isPresent()) {
                DishCategory newDishCategory = dishCategoryMapper.toEntity(category);
                DishCategory updatedDishCategory = dishCategoryService.updateDishCategory(newDishCategory);
                DishCategoryDTO dishCategoryDTO = dishCategoryMapper.toDTO(updatedDishCategory);
                return ResponseEntity.ok(dishCategoryDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // 删除菜品分类
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDishCategory(@PathVariable long id) {
       boolean deleted = dishCategoryService.deleteDishCategory(id);
       return deleted ?
               ResponseEntity.noContent().build() :
               ResponseEntity.notFound().build();

    }

}
