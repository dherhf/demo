package com.example.repository;

import com.example.model.dish.DishHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishHintRepository extends JpaRepository<DishHint, Long> {
    // 根据菜品ID查找所有提示
    List<DishHint> findByDishId(int dishId);

    // 根据提示类型查找
    List<DishHint> findByHintType(String hintType);

    // 根据优先级查找
    List<DishHint> findByPriority(String priority);

    // 根据菜品ID和提示类型查找
    List<DishHint> findByDishIdAndHintType(int dishId, String hintType);

    // 根据提示文本模糊查询
    List<DishHint> findByHintTextContaining(String hintText);

    // 查找高优先级的提示
    @Query("SELECT dh FROM DishHint dh WHERE dh.priority IN ('HIGH', 'URGENT') ORDER BY dh.priority DESC")
    List<DishHint> findHighPriorityHints();

    // 统计某个菜品的提示数量
    long countByDishId(int dishId);
}
