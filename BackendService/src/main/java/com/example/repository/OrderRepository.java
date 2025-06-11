package com.example.repository;

import com.example.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 根据客户ID查找订单
    List<Order> findByCustomerId(int customerId);

    // 根据桌子ID查找订单
    List<Order> findByDeskId(int deskId);

    // 根据员工ID查找订单
    List<Order> findByEmployeeId(int employeeId);

    // 根据订单状态查找
    List<Order> findByStatus(String status);

    // 根据创建日期查找订单
    List<Order> findByCreateDate(LocalDate createDate);

    // 根据日期范围查找订单
    List<Order> findByCreateDateBetween(LocalDate startDate, LocalDate endDate);

    // 查找今天的订单
    @Query("SELECT o FROM Order o WHERE o.createDate = CURRENT_DATE")
    List<Order> findTodayOrders();

    // 根据价格范围查找订单
    List<Order> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // 查找指定客户在指定日期的订单
    List<Order> findByCustomerIdAndCreateDate(int customerId, LocalDate createDate);

    // 查找指定桌子的当前活跃订单
    @Query("SELECT o FROM Order o WHERE o.desk.id = :deskId AND o.status NOT IN ('CANCELLED', 'SERVED')")
    List<Order> findActiveOrdersByDeskId(@Param("deskId") int deskId);

    // 统计指定日期的订单总数
    long countByCreateDate(LocalDate createDate);

    // 统计指定日期范围的订单总数
    long countByCreateDateBetween(LocalDate startDate, LocalDate endDate);

    // 计算指定日期的营业额
    @Query("SELECT SUM(o.price) FROM Order o WHERE o.createDate = :date AND o.status != 'CANCELLED'")
    BigDecimal getTotalRevenueByDate(@Param("date") LocalDate date);

    // 计算指定日期范围的营业额
    @Query("SELECT SUM(o.price) FROM Order o WHERE o.createDate BETWEEN :startDate AND :endDate AND o.status != 'CANCELLED'")
    BigDecimal getTotalRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 查找最近的订单
    @Query("SELECT o FROM Order o ORDER BY o.createDate DESC, o.id DESC")
    List<Order> findRecentOrders();
}
