package com.example.dto.dish;

import com.example.dto.dish.hint.DishHintDTO;
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
    @Mapping(target = "categoryName", source = "dishCategory.description")
    @Mapping(target = "hints", source = "dishHints", qualifiedByName = "mapDishHintsToDto")
    DishDTO toDTO(Dish dish);

    Dish toEntity(CreateDishRequest request);
    Dish toEntity(UpdateDishRequest request);

    @Named("mapDishHintsToDto")
    default List<DishHintDTO> mapDishHintsToDto(List<DishHint> dishHints) {
        if (CollectionUtils.isEmpty(dishHints)) {
            return null;
        }
        return dishHints.stream()
                .map(this::toDishHintDto)
                .collect(Collectors.toList());
    }

    default DishHintDTO toDishHintDto(DishHint hint) {
        if (hint == null) {
            return null;
        }
        DishHintDTO dto = new DishHintDTO();
        dto.setId(hint.getId());
        dto.setHintText(hint.getHintText());
        dto.setHintType(hint.getHintType());
        dto.setPriority(hint.getPriority());
        dto.setDishId(hint.getDish() != null ? hint.getDish().getId() : null);
        return dto;
    }

}
