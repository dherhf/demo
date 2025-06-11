package com.example.model.dish;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"dishHints", "dishCategory"})
@ToString(exclude = {"dishHints", "dishCategory"})
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_category_id")
    @JsonBackReference
    private DishCategory dishCategory;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DishHint> dishHints = new ArrayList<>();

    // 移除了 List<OrderItem> orderItems
    // 如果需要查询某个菜品的订单历史，通过Repository查询即可
}
