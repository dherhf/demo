package com.example.repository;

import com.example.model.desk.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {
    // 根据桌号查找
    Optional<Desk> findByCode(String code);

    // 查找所有开放的桌子
    List<Desk> findByOpenTrue();

    // 查找所有关闭的桌子
    List<Desk> findByOpenFalse();

    // 根据容量查找桌子
    List<Desk> findByCapacity(int capacity);

    // 查找容量大于等于指定人数的桌子
    List<Desk> findByCapacityGreaterThanEqual(int capacity);

    // 查找可用的桌子（开放且容量满足要求）
    @Query("SELECT d FROM Desk d WHERE d.open = true AND d.capacity >= :minCapacity")
    List<Desk> findAvailableDesks(@Param("minCapacity") int minCapacity);

    // 检查桌号是否已存在
    boolean existsByCode(String code);

}
