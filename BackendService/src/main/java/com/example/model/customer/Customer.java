package com.example.model.customer;

import com.example.model.order.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table
public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    // 顾客姓名
    private String name;
    // 顾客地址
    private String address;
    // 顾客手机号
    private String phone;
    // 顾客会员卡号
    private String cardID;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders;
}
