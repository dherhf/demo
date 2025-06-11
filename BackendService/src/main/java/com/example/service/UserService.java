package com.example.service;

import com.example.model.user.User;

import java.util.Optional;

public interface UserService {
    User registerUser(String username, String password);
    Optional<User> authenticateUser(String username, String password);
    boolean isUsernameExists(String username);
}