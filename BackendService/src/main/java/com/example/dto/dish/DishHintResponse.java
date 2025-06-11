package com.example.dto.dish;

import lombok.Data;

@Data
public class DishHintResponse {
    private int id;
    private String hintType;
    private String hintText;
    private String priority;
}
