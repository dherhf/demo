package com.example.model.desk;

import com.example.model.order.Order;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table
public class Desk {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    // 餐台代码
    private String code;
    // 餐台描述
    private String description;
    // 餐台容量
    private int capacity;
    // 餐台是否开放
    @ColumnDefault("true")
    private boolean open;
    // 当前活跃订单
    @Transient
    private Order currentOrder;
}
