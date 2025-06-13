package com.example.dto;

import com.example.model.dish.Dish;
import com.example.model.dish.DishHint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "categoryId", source = "dishCategory.id")
    @Mapping(target = "hints", source = "dishHints", qualifiedByName = "mapDishHintsToIds")
    DishDTO toDTO(Dish dish);

    Dish toEntity(DishDTO dishDTO);

    @Named("mapDishHintsToIds")
    default List<Long> mapDishHintsToIds(List<DishHint> dishHints) {
        if (CollectionUtils.isEmpty(dishHints)) {
            return List.of();
        }
        return dishHints.stream()
                .map(DishHint::getId)
                .map(Integer::longValue)
                .collect(Collectors.toList());
    }


}
