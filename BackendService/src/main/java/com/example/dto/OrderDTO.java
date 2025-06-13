package com.example.dto;

import com.example.model.customer.Customer;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    @NotBlank
    private LocalDate createDate;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private String status;
    @NotBlank
    private Long customerId;
    @NotBlank
    private Long deskId;
    @NotBlank
    private Long employeeId;
    @NotBlank
    private List<Long> orderItemIds;
}
