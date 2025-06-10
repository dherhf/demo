package com.example;

import com.example.model.user.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@SpringBootTest
class RepositoryTest {
    @Resource
    UserRepository userRepository;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("www.2005");
        userRepository.save(user);
    }

    @Test
    void userServiceVerifyLogin() {

    }
}
