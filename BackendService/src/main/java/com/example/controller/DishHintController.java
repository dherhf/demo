package com.example.controller;

import com.example.dto.DishHintDTO;
import com.example.service.DishHintService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DishHintDTO> createDishHint(@Valid @RequestBody DishHintDTO requestDTO) {
        try {
            DishHintDTO dishHintDTO = dishHintService.createDishHint(requestDTO);
            return ResponseEntity.ok().body(dishHintDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 更新现有菜品提示
    @PutMapping("/{id}")
    public ResponseEntity<DishHintDTO> updateDishHint(
            @PathVariable Long id,
            @Valid @RequestBody DishHintDTO requestDTO) {
        try {
            DishHintDTO dishHintDTO = dishHintService.updateDishHint(id,requestDTO);
            return ResponseEntity.ok().body(dishHintDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    // 删除菜品提示
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDishHint(@PathVariable Long id) {
        boolean deleted = dishHintService.deleteDishHint(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
