package com.example.model.dish;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"dishes"})
@ToString(exclude = {"dishes"})
@Entity
@Table(name = "dish_category")
public class DishCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private String tags;

    @OneToMany(mappedBy = "dishCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Dish> dishes = new ArrayList<>();
}
