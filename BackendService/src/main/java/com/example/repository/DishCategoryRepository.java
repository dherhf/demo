package com.example.repository;

import com.example.model.dish.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {
    // 根据标签模糊查询
    List<DishCategory> findByTagsContaining(String tags);

    // 根据描述模糊查询
    List<DishCategory> findByDescriptionContaining(String description);

    // 查询有菜品的分类
    @Query("SELECT DISTINCT dc FROM DishCategory dc WHERE dc.dishes IS NOT EMPTY")
    List<DishCategory> findCategoriesWithDishes();

    // 统计分类下的菜品数量
    @Query("SELECT COUNT(d) FROM Dish d WHERE d.dishCategory.id = :categoryId")
    Long countDishesByCategoryId(@Param("categoryId") int categoryId);
}
