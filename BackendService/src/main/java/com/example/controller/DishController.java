package com.example.controller;

import com.example.dto.dish.DishRequest;
import com.example.dto.dish.DishResponse;
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

    public DishController(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<DishResponse>> getAllDish() {
        List<Dish> dishes = dishService.getAllDishes();
        List<DishResponse> dishResponseList = dishes.stream()
                .map(this::convertToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(dishResponseList);
    }

    @PostMapping
    public ResponseEntity<DishResponse> createDish(@RequestBody DishRequest dishRequest) {
        Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(dishRequest.getDishCategoryId());
        if (dishCategory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Dish dish = convertToEntity(dishRequest, dishCategory.get());
        Dish savedDish = dishService.addDish(dish);
        DishResponse dishResponse = convertToResponse(savedDish);
        return new ResponseEntity<>(dishResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDishById(@PathVariable Long id) {
        Optional<Dish> dishOptional = dishService.findDishById(id);
        if (dishOptional.isPresent()) {
            Dish dish = dishOptional.get();
            DishResponse dishResponse = convertToResponse(dish);
            return ResponseEntity.ok(dishResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponse> updateDish(
            @PathVariable Long id,
            @RequestBody DishRequest dishRequest) {
        Optional<Dish> dishOptional = dishService.findDishById(id);
        if (dishOptional.isPresent()) {
            Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(dishRequest.getDishCategoryId());
            if (dishCategory.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Dish dish = convertToEntity(dishRequest, dishCategory.get());
            Dish updatedDish = dishService.addDish(dish);
            DishResponse dishResponse = convertToResponse(updatedDish);
            return ResponseEntity.ok(dishResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    private DishResponse convertToResponse(Dish dish){
        DishResponse dishResponse = new DishResponse();
        dishResponse.setId(dish.getId());
        dishResponse.setName(dish.getName());
        dishResponse.setCode(dish.getCode());
        dishResponse.setDescription(dish.getDescription());
        dishResponse.setImageUrl(dish.getImageUrl());
        return dishResponse;
    }

    private Dish convertToEntity(DishRequest dishRequest,DishCategory dishCategory) {
        Dish dish = new Dish();
        dish.setName(dishRequest.getName());
        dish.setCode(dishRequest.getCode());
        dish.setDescription(dishRequest.getDescription());
        dish.setImageUrl(dishRequest.getImageUrl());
        dish.setDishCategory(dishCategory);
        return dish;
    }
}