package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.ApiResponse;
import com.example.model.user.User;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     * @param loginRequest 登录请求对象，包含用户名和密码
     * @return 登录结果响应
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // 参数验证
            if ((loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty())
                    || (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty())) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "用户名或密码不能为空", null));
            }

            // 调用Service进行用户认证
            Optional<User> userOptional = userService.authenticateUser(
                    loginRequest.getUsername().trim(),
                    loginRequest.getPassword()
            );

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // 创建登录成功响应
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUserId(user.getId());
                loginResponse.setUsername(user.getUsername());

                return ResponseEntity.ok(
                        new ApiResponse<>(true, "登录成功", loginResponse)
                );
            } else {
                // 登录失败
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "用户名或密码错误", null));
            }

        } catch (Exception e) {
            // 处理其他异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "登录过程中发生错误: " + e.getMessage(), null));
        }
    }

}