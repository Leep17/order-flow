package ru.practicum.order_flow.mapper;

import ru.practicum.order_flow.dto.OrderDto;
import ru.practicum.order_flow.order.Order;

public class OrderMapper {
    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(order.getId(),
                order.getSku(),
                order.getQuantity(),
                order.getStatus(),
                order.getCreatedAt());
    }
}
