package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.DishCategoryRequest;
import com.example.model.dish.DishCategory;
import com.example.service.DishCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/dish-category")
@CrossOrigin(origins = "*")
public class DishCategoryController {
    private final DishCategoryService dishCategoryService;
    public DishCategoryController(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    // 获取所有菜品分类
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DishCategory>>> getAllDishCategories() {
        List<DishCategory> categories = dishCategoryService.findAllDishCategory();
        return ResponseEntity.ok(new ApiResponse<>(true, "获取所有菜品分类成功", categories));
    }

    // 根据ID获取菜品分类
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<DishCategory>>> getDishCategoryById(@PathVariable Long id) {
        Optional<DishCategory> category = dishCategoryService.findDishCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "获取菜品分类成功", category));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, "菜品分类不存在", null));
        }
    }

    // 添加菜品分类
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DishCategory>> addDishCategory(@Valid @RequestBody DishCategoryRequest category) {
        try {
            DishCategory dishCategory = new DishCategory();
            dishCategory.setTags(category.getTags());
            dishCategory.setDescription(category.getDescription());
            dishCategoryService.addDishCategory(dishCategory);
            return ResponseEntity.ok(new ApiResponse<>(true, "添加菜品分类成功", dishCategory));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(true, "添加菜品分类失败",null));
        }
    }

    // 更新菜品分类
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DishCategory>> updateDishCategory(
            @PathVariable long id,
            @Valid @RequestBody DishCategoryRequest category) {

        try {
            Optional<DishCategory> dishCategory = dishCategoryService.findDishCategoryById(id);
            if (dishCategory.isPresent()) {
                dishCategory.get().setTags(category.getTags());
                dishCategory.get().setDescription(category.getDescription());
                dishCategoryService.updateDishCategory(dishCategory.get());
                return ResponseEntity.ok(new ApiResponse<>(true, "更新菜品分类成功", dishCategory.get()));
            } else  {
                return ResponseEntity.ok(new ApiResponse<>(false, "更新菜品分类不存在",null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, "更新菜品分类失败",null));
        }

    }

    // 删除菜品分类
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDishCategory(@PathVariable Long id) {
        try {
            Optional<DishCategory> category = dishCategoryService.findDishCategoryById(id);
            if (category.isPresent()) {
                dishCategoryService.deleteDishCategory(category.get().getId());
                return ResponseEntity.ok(new ApiResponse<>(true, "删除菜品分类成功", null));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(false, "菜品分类不存在，删除失败", null));
            }
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse<>(false, "删除菜品分类失败",null));
        }
    }

}
