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
    private String code;
    private String description;
    private int capacity;
    @ColumnDefault("true")
    private boolean open;
    // 当前活跃订单
    @Transient
    private Order currentOrder;
}
