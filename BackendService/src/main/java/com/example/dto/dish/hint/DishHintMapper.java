package com.example.dto.dish.hint;

import com.example.model.dish.DishHint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishHintMapper {
    DishHintDTO toDTO(DishHint hint);
    DishHint toEntity(DishHintDTO dishHintDTO);
}
