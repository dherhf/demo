package com.example.model.dish;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dish_hint")
public class DishHint {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "hint_type")
    private String hintType;
    @Column(name = "hint_text")
    private String hintText;
    @Column(name = "priority")
    private String priority;
}
