package ru.practicum.order_flow.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.practicum.order_flow.order.Order;
import ru.practicum.order_flow.order.OrderStatus;
import ru.practicum.order_flow.repository.OrderRepository;
import ru.practicum.order_flow.service.OrderService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PendingOrderRecoveryScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Scheduled(fixedDelay = 30000)
    public void recoverPendingOrders() {
        List<Long> orderList = orderRepository.findAllByStatus(OrderStatus.PENDING).stream().map(Order::getId).toList();
        orderList.forEach(orderId -> {
            try {
                orderService.retry(orderId);
            } catch (RuntimeException e) {
                log.warn("Failed to recover pending order, orderId={}", orderId, e);
            }
        });
    }
}
