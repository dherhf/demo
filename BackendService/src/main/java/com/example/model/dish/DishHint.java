package com.example.model.dish;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"dish"})
@ToString(exclude = {"dish"})
@Entity
@Table(name = "dish_hint")
public class DishHint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hint_text")
    private String hintText;

    @Column(name = "hint_type")
    private String hintType;

    private String priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @JsonBackReference
    private Dish dish;
}
