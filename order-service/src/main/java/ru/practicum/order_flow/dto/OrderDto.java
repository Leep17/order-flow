package ru.practicum.order_flow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.order_flow.order.OrderStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private String sku;

    private int quantity;

    private OrderStatus status;

    private LocalDateTime createdAt;
}
