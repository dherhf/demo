package com.example.dto.desk;

import lombok.Data;

@Data
public class DeskResponse {
    private int id;
    private String code;
    private String description;
    private int capacity;
    private boolean open;
}
