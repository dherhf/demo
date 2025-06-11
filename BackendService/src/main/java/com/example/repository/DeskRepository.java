package com.example.repository;

import com.example.model.desk.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {
    Optional<Desk> findById(int id);
}
