package com.example.controller;

import com.example.dto.dish.DishCategoryRequest;
import com.example.dto.dish.DishCategoryResponse;
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

    public DishCategoryController(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    // 获取所有菜品分类
    @GetMapping
    public ResponseEntity<List<DishCategoryResponse>> getAllDishCategories() {
        List<DishCategory> categories = dishCategoryService.findAllDishCategory();

        List<DishCategoryResponse> dishCategoryResponses = categories.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dishCategoryResponses);
    }

    // 添加菜品分类
    @PostMapping
    public ResponseEntity<DishCategoryResponse> addDishCategory(@Valid @RequestBody DishCategoryRequest category) {
        try {
            DishCategory dishCategory = new DishCategory();
            dishCategory.setTags(category.getTags());
            dishCategory.setDescription(category.getDescription());
            DishCategoryResponse dishCategoryResponse = convertToResponse(dishCategoryService.addDishCategory(dishCategory));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(dishCategoryResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 根据ID获取菜品分类
    @GetMapping("/{id}")
    public ResponseEntity<DishCategoryResponse> getDishCategoryById(@PathVariable Long id) {
        Optional<DishCategory> category = dishCategoryService.findDishCategoryById(id);

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DishCategoryResponse dishCategoryResponse = convertToResponse(category.get());
        return ResponseEntity.ok(dishCategoryResponse);
    }



    // 更新菜品分类
    @PutMapping("/{id}")
    public ResponseEntity<DishCategoryResponse> updateDishCategory(
            @PathVariable long id,
            @Valid @RequestBody DishCategoryRequest category) {
        try {
            Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(id);
            if (dishCategory.isPresent()) {
                dishCategory.get().setTags(category.getTags());
                dishCategory.get().setDescription(category.getDescription());
                DishCategoryResponse dishCategoryResponse = convertToResponse(dishCategoryService.updateDishCategory(dishCategory.get()));
                return ResponseEntity.ok(dishCategoryResponse);
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

    //
    private DishCategoryResponse convertToResponse(DishCategory dishCategory) {
        DishCategoryResponse dishCategoryResponse = new DishCategoryResponse();
        dishCategoryResponse.setTags(dishCategory.getTags());
        dishCategoryResponse.setDescription(dishCategory.getDescription());
        return dishCategoryResponse;

    }

}
