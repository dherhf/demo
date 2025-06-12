package com.example.dto.customer;

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
    default List<Integer> mapOrdersToIds(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        return orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());
    }

    CustomerDTO toCreateDTO(Customer customer);

    Customer toCreateEntity(CreateCustomerRequest request);

    Customer toUpdateEntity(UpdateCustomerRequest request);
}
