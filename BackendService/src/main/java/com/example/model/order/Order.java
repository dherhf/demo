package com.example.model.order;

import com.example.model.customer.Customer;
import com.example.model.desk.Desk;
import com.example.model.employee.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "`order`")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    // 订单创建时间
    private LocalDate createDate;
    // 订单金额
    private double price;
    // 订单状态
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    // 订单内容
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    // 餐台ID
    @ManyToOne
    @JoinColumn(name = "desk_id")
    private Desk desk;
    // 顾客ID
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    // 服务员工ID
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
