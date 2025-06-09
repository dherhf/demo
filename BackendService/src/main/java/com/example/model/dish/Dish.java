package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "dish")
public class Dish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToMany
    @JoinTable(
            name = "dish_dish_hint",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_hint_id")
    )
    private Set<DishHint> hints = new HashSet<>();
}
