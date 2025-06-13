package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank
    private int age;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 11, max = 11)
    private String phone;
    private List<Long> orderIds;
}
