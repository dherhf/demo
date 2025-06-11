package com.example.model.order;

import com.example.model.dish.Dish;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"dish", "order"})
@ToString(exclude = {"dish", "order"})
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    // 保留对Dish的引用，因为OrderItem需要知道点的是什么菜
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @JsonBackReference
    private Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
