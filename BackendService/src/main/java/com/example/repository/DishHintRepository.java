package com.example.repository;

import com.example.model.dish.DishHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishHintRepository extends JpaRepository<DishHint, Long> {

}
