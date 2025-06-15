package com.example.service;

import com.example.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@SpringBootTest
class ServiceTest {
    @Autowired
    DishCategoryService dishCategoryService;

    @Autowired
    DishService dishService;

    @Autowired
    UserService userService;

    @Transactional
    @Test
    void contextLoads() {
        Optional<User> user = userService.authenticateUser("admin", "www.2005");
        if(user.isPresent()) {
            System.out.println(user.get().getUsername());
        }


    }
}
