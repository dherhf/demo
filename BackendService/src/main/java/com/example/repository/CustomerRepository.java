package com.example.repository;

import com.example.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 根据手机号查找客户
    Optional<Customer> findByPhone(String phone);

    // 根据身份证号查找客户
    Optional<Customer> findByCardID(String cardID);

    // 根据姓名模糊查询
    List<Customer> findByNameContaining(String name);

    // 根据地址模糊查询
    List<Customer> findByAddressContaining(String address);

    // 检查手机号是否已存在
    boolean existsByPhone(String phone);

    // 检查身份证号是否已存在
    boolean existsByCardID(String cardID);

}
