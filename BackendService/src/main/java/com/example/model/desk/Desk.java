// Desk.java
package com.example.model.desk;

import com.example.model.order.Order;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@Entity
@Table(name = "desk")
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int capacity;

    private String code;

    private String description;

    private boolean open;

    @OneToMany(mappedBy = "desk", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
}
