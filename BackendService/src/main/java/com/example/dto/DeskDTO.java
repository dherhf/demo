package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeskDTO {
    @NotBlank
    private int id;
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotBlank
    private int capacity;
    @NotBlank
    private boolean open;
}
