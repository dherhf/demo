package com.example.service.impl;


import com.example.dto.DishHintDTO;
import com.example.dto.DishHintMapper;
import com.example.model.dish.DishHint;
import com.example.repository.DishHintRepository;
import com.example.repository.DishRepository;
import com.example.service.DishHintService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DishHintServiceImpl implements DishHintService {
    @Autowired
    private DishHintRepository dishHintRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishHintMapper dishHintMapper;

    public DishHintDTO getDishHintById(Long id) {
        Optional<DishHint>  dishHint = dishHintRepository.findById(id);
        if (dishHint.isEmpty()){
            throw new EntityNotFoundException("not found");
        }
        return dishHintMapper.toDTO(dishHint.get());
    }

    public DishHintDTO createDishHint(DishHintDTO dishHintDTO) {
        dishHintDTO.setId(null);
        if (dishRepository.existsById(dishHintDTO.getDishId())){
            throw new EntityNotFoundException("dish not found");
        }
        DishHint dishHint = dishHintMapper.toEntity(dishHintDTO);
        DishHint save = dishHintRepository.save(dishHint);
        return dishHintMapper.toDTO(save);
    }

    public DishHintDTO updateDishHint(Long id, DishHintDTO dishHintDTO) {
        if (!id.equals(dishHintDTO.getId())) {
            throw  new IllegalArgumentException("bad request");
        }

        if (!dishHintRepository.existsById(id)){
            throw  new EntityNotFoundException("not found");
        }

        dishHintDTO.setId(id);
        DishHint save = dishHintRepository.save(dishHintMapper.toEntity(dishHintDTO));
        return dishHintMapper.toDTO(save);
    }

    public boolean deleteDishHint(Long id) {
        if (dishHintRepository.existsById(id)){
            dishHintRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
