package ru.practicum.order_flow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.order_flow.order.Order;
import ru.practicum.order_flow.order.OrderStatus;

import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order, Long> {
 Collection<Order> findAllByStatus(OrderStatus orderStatus);
}
