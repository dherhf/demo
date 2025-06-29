package com.example.repository;

import com.example.model.dish.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {
}
