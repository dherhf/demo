package com.example.dto;

import com.example.model.employee.Employee;
import com.example.model.order.Order;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeMapper {
    @Mapping(target = "ordersIds", source = "orders", qualifiedByName = "mapOrdersToIds")
    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);
    @Named("mapOrdersToIds")
    default List<Long> mapOrdersToIds(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        return orders.stream()
                .map(Order::getId)
                .map(Integer::longValue)
                .collect(Collectors.toList());
    }
}
