package ru.practicum.order_flow.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.order_flow.order.Order;
import ru.practicum.order_flow.repository.OrderRepository;
import ru.practicum.order_flow.scheduler.PendingOrderRecoveryScheduler;

import java.util.List;

import static org.mockito.Mockito.*;
import static ru.practicum.order_flow.order.OrderStatus.PENDING;

@ExtendWith(MockitoExtension.class)
public class PendingOrderRecoverySchedulerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PendingOrderRecoveryScheduler scheduler;


    @Test
    void recoverTest() {
        Order order1  = new Order();
        order1.setId(1L);
        order1.setSku("IPHONE-15-BLACK");
        order1.setQuantity(2);
        order1.setStatus(PENDING);

        Order order2  = new Order();
        order2.setId(2L);
        order2.setSku("IPHONE-15-WHITE");
        order2.setQuantity(2);
        order2.setStatus(PENDING);

        when(orderRepository.findAllByStatus(PENDING))
                .thenReturn(List.of(order1, order2));

        scheduler.recoverPendingOrders();

        verify(orderRepository).findAllByStatus(PENDING);
        verify(orderService).retry(order1.getId());
        verify(orderService).retry(order2.getId());
    }

    @Test
    void exceptionTest() {
        Order order1  = new Order();
        order1.setId(1L);
        order1.setSku("IPHONE-15-BLACK");
        order1.setQuantity(2);
        order1.setStatus(PENDING);

        Order order2  = new Order();
        order2.setId(2L);
        order2.setSku("IPHONE-15-WHITE");
        order2.setQuantity(2);
        order2.setStatus(PENDING);

        when(orderRepository.findAllByStatus(PENDING))
                .thenReturn(List.of(order1, order2));

        when(orderService.retry(order1.getId())).thenThrow(new RuntimeException());

        scheduler.recoverPendingOrders();

        verify(orderRepository).findAllByStatus(PENDING);
        verify(orderService).retry(order1.getId());
        verify(orderService).retry(order2.getId());
    }

    @Test
    void nullTest() {

        when(orderRepository.findAllByStatus(PENDING))
                .thenReturn(List.of());

        scheduler.recoverPendingOrders();

        verify(orderRepository).findAllByStatus(PENDING);
        verify(orderService, never()).retry(anyLong());
    }

}
