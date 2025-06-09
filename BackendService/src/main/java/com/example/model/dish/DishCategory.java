package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dish_category")
public class DishCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private int id;
    @Column(name = "tags")
    private String tags;
    @Column(name = "description")
    private String description;

}
