package ru.practicum.order_flow.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.order_flow.dto.NewOrderDto;
import ru.practicum.order_flow.exception.OrderNotFoundException;
import ru.practicum.order_flow.order.Order;
import ru.practicum.order_flow.order.OrderStatus;
import ru.practicum.order_flow.repository.OrderRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с id=" + id + " не найден"));
    }

    @Transactional
    public Order save(NewOrderDto newOrderDto) {

        Order order = new Order();
        order.setSku(newOrderDto.getSku());
        order.setQuantity(newOrderDto.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
}
