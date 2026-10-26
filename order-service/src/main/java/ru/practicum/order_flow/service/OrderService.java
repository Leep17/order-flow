package ru.practicum.order_flow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.order_flow.client.InventoryClient;
import ru.practicum.order_flow.dto.NewOrderDto;
import ru.practicum.order_flow.exception.InsufficientInventoryException;
import ru.practicum.order_flow.exception.InventoryItemNotFoundException;
import ru.practicum.order_flow.exception.OrderNotFoundException;
import ru.practicum.order_flow.exception.OrderStatusConflictException;
import ru.practicum.order_flow.order.Order;
import ru.practicum.order_flow.order.OrderStatus;
import ru.practicum.order_flow.repository.OrderRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с id=" + id + " не найден"));
    }


    public Order save(NewOrderDto newOrderDto) {

        Order order = new Order();
        order.setSku(newOrderDto.getSku());
        order.setQuantity(newOrderDto.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        try {
            inventoryClient.reserve(newOrderDto.getSku(), newOrderDto.getQuantity(), order.getId());
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } catch (InventoryItemNotFoundException|InsufficientInventoryException e) {
            order.setStatus(OrderStatus.REJECTED);
            orderRepository.save(order);
            throw e;
        }
        return order;
    }

    public Order retry(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с id=" + id + " не найден"));
        if (order.getStatus().equals(OrderStatus.PENDING)) {
            try {
                inventoryClient.reserve(order.getSku(), order.getQuantity(), id);
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);
            } catch (InventoryItemNotFoundException|InsufficientInventoryException e) {
                order.setStatus(OrderStatus.REJECTED);
                orderRepository.save(order);
                throw e;
            }
        } else {
            throw new OrderStatusConflictException("У заказа " + id + " статус не PENDING");
        }
        return order;
    }
}
