package com.example.dto.customer;

import lombok.Data;

@Data
public class CustomerResponse {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String cardID;
}
