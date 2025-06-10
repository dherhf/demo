package com.example.model.employee;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Employee {
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //生成策略，这里配置为自增
    @Id
    private int id;
    // 员工名字
    private String name;
    // 员工年龄
    private int age;
    // 员工手机号
    private String phone;
}
