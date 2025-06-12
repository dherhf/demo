package com.example.controller;

import com.example.dto.dish.*;
import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
import com.example.service.DishCategoryService;
import com.example.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/dish")
@CrossOrigin(origins = "*")
public class DishController {

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;
    private final DishMapper  dishMapper;

    public DishController(DishService dishService, DishCategoryService dishCategoryService, DishMapper dishMapper) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
        this.dishMapper = dishMapper;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDish() {
        List<Dish> dishes = dishService.getAllDishes();
        List<DishDTO> dishDTOList = dishes.stream()
                .map(dishMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dishDTOList);
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody CreateDishRequest dishRequest) {
        Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(dishRequest.getCategoryId());
        if (dishCategory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Dish dish = dishMapper.toEntity(dishRequest);
        Dish savedDish = dishService.addDish(dish);
        DishDTO dishDTO = dishMapper.toDTO(savedDish);
        return ResponseEntity.status(HttpStatus.CREATED).body(dishDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Long id) {
        Optional<Dish> dishOptional = dishService.findDishById(id);
        if (dishOptional.isPresent()) {
            Dish dish = dishOptional.get();
            DishDTO dishDTO = dishMapper.toDTO(dish);
            return ResponseEntity.ok(dishDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(
            @PathVariable Long id,
            @RequestBody UpdateDishRequest dishRequest) {
        if (dishRequest.getId() != null && !dishRequest.getId().equals(id)){
            return ResponseEntity.badRequest().build();
        }
        Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(dishRequest.getCategoryId());
        if (dishCategory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Dish dish = dishMapper.toEntity(dishRequest);
        Dish newDish = dishService.updateDish(dish);
        DishDTO dishDTO = dishMapper.toDTO(newDish);
        return ResponseEntity.ok(dishDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Dish> dishOptional = dishService.findDishById(id);
        if (dishOptional.isPresent()) {

            dishService.deleteDishById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}