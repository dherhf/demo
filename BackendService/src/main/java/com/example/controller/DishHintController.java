package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.model.dish.DishHint;
import com.example.service.DishHintService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DishHint>> getDishHintById(@PathVariable Long id) {
        Optional<DishHint> dishHint = dishHintService.getDishHint(id);
        return dishHint.map(hint -> ResponseEntity.ok(new ApiResponse<>(true, "", hint)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "菜品提示不存在，ID: " + id, null)));
    }


}
