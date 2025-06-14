package com.example.dto;

import com.example.model.employee.Employee;
import com.example.model.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "orderIds", source = "orders", qualifiedByName = "mapOrdersToIds")
    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);

    Employee toEntity(EmployeeDTO employeeDTO, List<Order> orders);

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
