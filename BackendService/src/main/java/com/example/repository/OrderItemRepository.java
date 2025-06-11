package com.example.repository;

import com.example.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 根据订单ID查找订单项
    List<OrderItem> findByOrderId(int orderId);

    // 根据菜品ID查找订单项
    List<OrderItem> findByDishId(int dishId);

    // 根据订单ID和菜品ID查找订单项
    List<OrderItem> findByOrderIdAndDishId(int orderId, int dishId);

    // 统计某个菜品的总销量
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.dish.id = :dishId")
    Long getTotalQuantityByDishId(@Param("dishId") int dishId);

    // 统计某个菜品在指定日期范围的销量
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi " +
            "WHERE oi.dish.id = :dishId AND oi.order.createDate BETWEEN :startDate AND :endDate")
    Long getQuantityByDishIdAndDateRange(@Param("dishId") int dishId,
                                         @Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);

    // 统计某个订单的总菜品数量
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.order.id = :orderId")
    Long getTotalQuantityByOrderId(@Param("orderId") int orderId);

    // 查找热门菜品（按销量排序）
    @Query("SELECT oi.dish.id, oi.dish.name, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.dish.id, oi.dish.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findPopularDishes();

    // 查找指定日期范围内的热门菜品
    @Query("SELECT oi.dish.id, oi.dish.name, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "WHERE oi.order.createDate BETWEEN :startDate AND :endDate " +
            "GROUP BY oi.dish.id, oi.dish.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findPopularDishesByDateRange(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    // 统计订单项总数
    @Query("SELECT COUNT(oi) FROM OrderItem oi WHERE oi.order.id = :orderId")
    Long countByOrderId(@Param("orderId") int orderId);
}
