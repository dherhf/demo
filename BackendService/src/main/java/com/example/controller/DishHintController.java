package com.example.controller;

import com.example.dto.dish.DishHintRequest;
import com.example.dto.dish.DishHintResponse;
import com.example.model.dish.DishHint;
import com.example.service.DishHintService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/dish-hint")
@CrossOrigin(origins = "*")
public class DishHintController {
    private final DishHintService dishHintService;

    public DishHintController(DishHintService dishHintService) {
        this.dishHintService = dishHintService;
    }

    // 创建新的菜品提示
    @PostMapping
    public ResponseEntity<DishHintResponse> createDishHint(@Valid @RequestBody DishHintRequest request) {
        try {
            DishHint dishHint = new DishHint();
            dishHint.setHintType(request.getHintType());
            dishHint.setHintText(request.getHintText());
            dishHint.setPriority(request.getPriority());

            DishHint savedHint = dishHintService.addDishHint(dishHint);
            DishHintResponse response = convertToResponse(savedHint);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 根据ID获取单个菜品提示
    @GetMapping("/{id}")
    public ResponseEntity<DishHintResponse> getDishHintById(@PathVariable Long id) {
        Optional<DishHint> dishHint = dishHintService.getDishHintById(id);
        return dishHint.map(hint -> ResponseEntity.ok(convertToResponse(hint)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 更新现有菜品提示
    @PutMapping("/{id}")
    public ResponseEntity<DishHintResponse> updateDishHint(
            @PathVariable Long id,
            @Valid @RequestBody DishHintRequest request) {
        try {
            Optional<DishHint> existingHint = dishHintService.getDishHintById(id);
            if (existingHint.isPresent()) {
                DishHint dishHint = existingHint.get();
                dishHint.setHintType(request.getHintType());
                dishHint.setHintText(request.getHintText());
                dishHint.setPriority(request.getPriority());

                DishHint updatedHint = dishHintService.updateDishHint(dishHint);
                DishHintResponse response = convertToResponse(updatedHint);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 删除菜品提示
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDishHint(@PathVariable Long id) {
        try {
            Optional<DishHint> existingHint = dishHintService.getDishHintById(id);
            if (existingHint.isPresent()) {
                dishHintService.deleteDishHint(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 辅助方法：将实体转换为响应DTO
    private DishHintResponse convertToResponse(DishHint dishHint) {
        DishHintResponse response = new DishHintResponse();
        response.setHintType(dishHint.getHintType());
        response.setHintText(dishHint.getHintText());
        response.setPriority(dishHint.getPriority());
        return response;
    }

}
