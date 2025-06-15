package com.example.dto;

import jakarta.validation.constraints.NotNull;
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
    private Long id;
    private LocalDate createDate;
    @NotNull
    private BigDecimal price;
    private String status;
    @NotNull
    private Long customerId;
    @NotNull
    private Long deskId;
    @NotNull
    private Long employeeId;
    private List<Long> orderItemIds;
}
