package com.example.service.impl;


import com.example.model.dish.DishHint;
import com.example.repository.DishHintRepository;
import com.example.service.DishHintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DishHintServiceImpl implements DishHintService {
    @Autowired
    private DishHintRepository dishHintRepository;

    public Optional<DishHint> getDishHintById(long id) {
        return dishHintRepository.findById(id);
    }

    public DishHint addDishHint(DishHint dishHint) {
        return dishHintRepository.save(dishHint);
    }

    public void deleteDishHint(long id) {
        dishHintRepository.deleteById(id);
    }

    public DishHint updateDishHint(DishHint dishHint) {
        return dishHintRepository.save(dishHint);
    }
}
