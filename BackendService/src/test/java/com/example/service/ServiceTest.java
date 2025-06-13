package com.example.service;

import com.example.repository.CustomerRepository;
import com.example.repository.DishCategoryRepository;
import com.example.repository.DishRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
class ServiceTest {
    @Autowired
    DishCategoryService dishCategoryService;

    @Autowired
    DishCategoryRepository dishCategoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DishRepository dishRepository;

    @Transactional
    @Test
    void contextLoads() {
        System.out.println(dishRepository.findById(1L));
    }
}
