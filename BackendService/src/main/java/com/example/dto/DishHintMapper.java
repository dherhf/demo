package com.example.dto;

import com.example.model.dish.DishHint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishHintMapper {
    @Mapping(target = "dishId" ,source = "dish.id")
    DishHintDTO toDTO(DishHint hint);
    DishHint toEntity(DishHintDTO dishHintDTO);
}
