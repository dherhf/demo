package com.example.dto;

import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class MapperTest {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    DishCategoryMapper dishCategoryMapper;
    @Test
    void toDTO() {
        DishCategory dishCategory = new DishCategory();
        dishCategory.setId(1);
        dishCategory.setDescription("dish");
        dishCategory.setTags("tags");
        Dish  dish = new Dish();
        dish.setId(1);
        dish.setDishCategory(dishCategory);
        dish.setName("dish");
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish);
        dishCategory.setDishes(dishes);
        DishCategoryDTO dishCategoryDTO = dishCategoryMapper.toDTO(dishCategory);
        System.out.println(dishCategoryDTO);
    }
    @Test
    void toEntity() {
        DishCategory dishCategory = new DishCategory();
        dishCategory.setId(1);
        dishCategory.setDescription("dish");
        dishCategory.setTags("tags");
        Dish  dish = new Dish();
        dish.setId(1);
        dish.setDishCategory(dishCategory);
        dish.setName("dish");
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish);
        dishCategory.setDishes(dishes);
        DishCategoryDTO dishCategoryDTO = dishCategoryMapper.toDTO(dishCategory);
        DishCategory newDishCategory = dishCategoryMapper.toEntity(dishCategoryDTO, dishes);
        System.out.println(newDishCategory.getDishes());


    }

}