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
public class CustomerDTO {
    private Long id;
    @NotBlank(message = "顾客名字不能为空")
    private String name;
    @NotBlank(message = "顾客地址不能为空")
    private String address;
    @NotBlank(message = "顾客手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号格式不对")
    private String phone;
    private String cardID;
    private List<Long> orderIds;
}
