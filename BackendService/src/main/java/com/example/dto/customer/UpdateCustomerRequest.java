package com.example.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {
    @NotNull(message = "ID不能为空")
    private Integer id;
    @NotBlank(message = "顾客名字不能为空")
    private String name;
    @NotBlank(message = "顾客地址不能为空")
    private String address;
    @NotBlank(message = "顾客手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号格式不对")
    private String phone;
    private String cardID;
}
