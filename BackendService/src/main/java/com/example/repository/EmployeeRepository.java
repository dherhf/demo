package com.example.repository;

import com.example.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // 根据手机号查找员工
    Optional<Employee> findByPhone(String phone);

    // 根据姓名查找员工
    List<Employee> findByName(String name);

    // 根据姓名模糊查询
    List<Employee> findByNameContaining(String name);

    // 根据年龄范围查找员工
    List<Employee> findByAgeBetween(int minAge, int maxAge);

    // 查找年龄大于指定值的员工
    List<Employee> findByAgeGreaterThan(int age);

    // 查找年龄小于指定值的员工
    List<Employee> findByAgeLessThan(int age);

    // 检查手机号是否已存在
    boolean existsByPhone(String phone);

    // 统计员工总数
    @Query("SELECT COUNT(e) FROM Employee e")
    Long getTotalEmployeeCount();
}
