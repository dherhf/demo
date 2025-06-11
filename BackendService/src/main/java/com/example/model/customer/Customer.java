package com.example.model.customer;

import com.example.model.order.Order;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;
    private String cardID;

    private String name;

    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
}
