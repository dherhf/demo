package com.example.repository;

import com.example.model.dish.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {
}
