package com.example.service;

import com.example.model.user.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 注册新用户
     * @param username 用户名
     * @param password 明文密码
     * @return 注册成功返回用户对象，失败返回null
     * @throws IllegalArgumentException 当用户名已存在时抛出异常
     */
    @Transactional
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

    /**
     * 验证用户登录
     * @param username 用户名
     * @param password 明文密码
     * @return 登录成功返回用户对象，失败返回空Optional
     */
    public Optional<User> authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> {
                    String inputHash = calculateHash(password, user.getSalt());
                    return inputHash.equals(user.getPassword());
                });
    }

    /**
     * 计算密码哈希值（与User实体中的方法保持一致）
     */
    private String calculateHash(String password, String salt) {
        String combined = password + salt;
        byte[] hashBytes = DigestUtils.md5Digest(combined.getBytes());
        return DigestUtils.md5DigestAsHex(hashBytes);
    }

    /**
     * 检查用户名是否存在
     */
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 通过ID获取学生
     */
    public Optional<User> getUserById(String  id){
        return userRepository.findById(id);
    }
}