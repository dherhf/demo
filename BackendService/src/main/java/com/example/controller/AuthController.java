package com.example.controller;

import com.example.dto.user.LoginRequest;
import com.example.dto.user.LoginResponse;
import com.example.model.user.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户认证
     *
     * @param loginRequest 登录请求对象，包含用户名和密码
     * @return 认证结果
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        // 参数验证
        try {
            if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty() || loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // 调用Service进行用户认证
            Optional<User> userOptional = userService.authenticateUser(loginRequest.getUsername().trim(), loginRequest.getPassword());

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // 创建认证成功响应
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUserId(user.getId());
                loginResponse.setUsername(user.getUsername());

                return ResponseEntity.ok(loginResponse);
            } else {
                // 认证失败
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
