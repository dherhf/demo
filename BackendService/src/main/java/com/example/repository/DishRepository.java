package com.example.repository;

import com.example.model.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    // 根据菜品代码查找
    Optional<Dish> findByCode(String code);

    // 根据菜品名称查找
    Optional<Dish> findByName(String name);

    // 根据名称模糊查询
    List<Dish> findByNameContaining(String name);

    // 根据分类ID查找菜品
    List<Dish> findByDishCategoryId(int dishCategoryId);

    // 根据分类ID查找菜品（使用关联查询）
    @Query("SELECT d FROM Dish d WHERE d.dishCategory.id = :categoryId")
    List<Dish> findAllByDishCategoryId(@Param("categoryId") int categoryId);

    // 查找有图片的菜品
    List<Dish> findByImageUrlIsNotNull();

    // 查找没有图片的菜品
    List<Dish> findByImageUrlIsNull();

    // 根据分类标签查找菜品
    @Query("SELECT d FROM Dish d WHERE d.dishCategory.tags LIKE %:tag%")
    List<Dish> findByDishCategoryTag(@Param("tag") String tag);

    // 检查菜品代码是否已存在
    boolean existsByCode(String code);

    // 检查菜品名称是否已存在
    boolean existsByName(String name);

    // 统计分类下的菜品数量
    long countByDishCategoryId(int dishCategoryId);
}
