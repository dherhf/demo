package com.example.dto;

import com.example.model.order.Order;
import com.example.model.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "deskId", source = "desk.id")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "orderItemIds", source = "orderItems", qualifiedByName = "mapOrderItemsToIds")
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);

    @Named("mapOrderItemsToIds")
    default List<Long> mapOrderItemsToIds(List<OrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            return List.of();
        }
        return orderItems.stream()
                .map(OrderItem::getId)
                .map(Integer::longValue)
                .collect(Collectors.toList());
    }
}
