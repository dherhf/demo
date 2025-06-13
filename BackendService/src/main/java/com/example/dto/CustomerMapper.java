package com.example.dto;

import com.example.model.customer.Customer;
import com.example.model.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "orderIds", source = "orders", qualifiedByName = "mapOrdersToIds")
    CustomerDTO toDto(Customer customer);

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

    Customer toEntity(CustomerDTO customerDTO);

    @Mapping(target = "orders", source = "orders")
    Customer toEntity(CustomerDTO customerDTO, List<Order> orders);
}
