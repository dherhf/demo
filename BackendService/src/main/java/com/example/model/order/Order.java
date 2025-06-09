package com.example.model.order;

import com.example.model.desk.Desk;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "order")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private int customerId;
    private LocalDate createDate;
    private double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "desk_id")
    private Desk desk;
}
