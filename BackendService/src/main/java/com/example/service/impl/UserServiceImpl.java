package com.example.service.impl;

import com.example.model.user.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String username, String password) {
        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 创建新用户对象
        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPassword(password); // 密码会在@PrePersist中自动加盐哈希

        // 保存用户到数据库
        return userRepository.save(newUser);
    }

    public Optional<User> authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> {
                    String inputHash = calculateHash(password, user.getSalt());
                    return inputHash.equals(user.getPassword());
                });
    }

    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private String calculateHash(String password, String salt) {
        String combined = password + salt;
        byte[] hashBytes = DigestUtils.md5Digest(combined.getBytes());
        return DigestUtils.md5DigestAsHex(hashBytes);
    }

}
