package com.example.service;

import com.example.model.dish.DishHint;
import com.example.repository.DishHintRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishHintService {
    private final DishHintRepository dishHintRepository;
    public DishHintService(DishHintRepository dishHintRepository) {
        this.dishHintRepository = dishHintRepository;
    }

    public Optional<DishHint> getDishHint(long id) {
        return dishHintRepository.findById(id);
    }

    public void addDishHint(DishHint dishHint) {
        dishHintRepository.save(dishHint);
    }

    public void deleteDishHint(long id) {
        dishHintRepository.deleteById(id);
    }

    public void updateDishHint(DishHint dishHint) {
        dishHintRepository.save(dishHint);
    }
}
