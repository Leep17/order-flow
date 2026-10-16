package ru.practicum.order_flow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.order_flow.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
