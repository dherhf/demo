package com.example.controller;

import com.example.dto.DishCategoryDTO;
import com.example.service.DishCategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<DishCategoryDTO>> getAllDishCategories() {
        try {
            List<DishCategoryDTO> responseDTO = dishCategoryService.getAllDishCategory();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 添加菜品分类
    @PostMapping
    public ResponseEntity<DishCategoryDTO> createDishCategory(@Valid @RequestBody DishCategoryDTO requestDTO) {
        try {
            DishCategoryDTO responseDTO = dishCategoryService.createDishCategory(requestDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // 根据ID获取菜品分类
    @GetMapping("/{id}")
    public ResponseEntity<DishCategoryDTO> getDishCategoryById(@PathVariable Long id) {
        try {
            DishCategoryDTO responseDTO = dishCategoryService.getDishCategoryById(id);
            return ResponseEntity.ok().body(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // 更新菜品分类
    @PutMapping("/{id}")
    public ResponseEntity<DishCategoryDTO> updateDishCategory(
            @PathVariable Long id,
            @Valid @RequestBody DishCategoryDTO requestDTO) {
        try {
            DishCategoryDTO responseDTO = dishCategoryService.updateDishCategory(id, requestDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
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
