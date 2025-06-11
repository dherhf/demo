package com.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String userId;
    private String username;
    private String token; // 如果使用JWT或其他token
    @Builder.Default
    private String  loginTime = LocalDate.now().toString();

    // 自定义构造函数，确保loginTime自动设置
    public LoginResponse(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.loginTime = LocalDate.now().toString();
    }
}
