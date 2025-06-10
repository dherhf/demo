package com.example.model.order;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private int dishId;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
