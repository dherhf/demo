package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.DishHintRequest;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DishHint>> getDishHintById(@PathVariable Long id) {
        Optional<DishHint> dishHint = dishHintService.getDishHintById(id);
        return dishHint.map(hint -> ResponseEntity.ok(new ApiResponse<>(true, "", hint)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "菜品提示不存在，ID: " + id, null)));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DishHint>>  addDishHint(@Valid @RequestBody DishHintRequest hint) {
        try {
            DishHint dishHint  = new DishHint();
            dishHint.setHintType(hint.getHintType());
            dishHint.setHintText(hint.getHintText());
            dishHint.setPriority(hint.getPriority());
            dishHintService.addDishHint(dishHint);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "", dishHint));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false,"",null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DishHint>> updateDishHint(
            @PathVariable long id,
            @Valid @RequestBody DishHintRequest hint){
        try {
            Optional<DishHint> dishHint = dishHintService.getDishHintById(id);
            if(dishHint.isPresent()) {
                dishHint.get().setHintType(hint.getHintType());
                dishHint.get().setHintText(hint.getHintText());
                dishHint.get().setPriority(hint.getPriority());
                dishHintService.updateDishHint(dishHint.get());
                return ResponseEntity.ok(new ApiResponse<>(true, "", dishHint.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false,"<UNK>ID: " + id, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false,"",null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDishHint(@PathVariable Long id) {
        try {
            dishHintService.deleteDishHint(id);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "",null)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false,"服务器错误",null));
        }
    }


}
