package com.example.dto;

import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DishCategoryMapper {
    @Mapping(target = "dishIds", source = "dishes", qualifiedByName = "mapDishesToIds")
    DishCategoryDTO toDTO(DishCategory dishCategory);

    DishCategory toEntity(DishCategoryDTO dishCategoryDTO);

    @Named("mapDishesToIds")
    default List<Long> mapDishesToIds(List<Dish> dishes) {
        if (dishes == null) {
            return List.of();
        }
        return dishes.stream()
                .map(Dish::getId)
                .map(Integer::longValue)
                .collect(Collectors.toList());
    }
}
