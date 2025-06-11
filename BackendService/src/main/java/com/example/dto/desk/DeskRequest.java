package com.example.dto.desk;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeskRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotBlank
    private int capacity;
    @NotBlank
    private boolean open;
}
