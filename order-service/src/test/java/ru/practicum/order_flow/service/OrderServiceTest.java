package ru.practicum.order_flow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.never;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.order_flow.client.InventoryClient;
import ru.practicum.order_flow.exception.*;
import ru.practicum.order_flow.order.Order;
import ru.practicum.order_flow.order.OrderStatus;
import ru.practicum.order_flow.repository.OrderRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private OrderService orderService;


    @Test
    void updateOrderStatus() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        orderService.retry(order.getId());

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        verify(orderRepository).save(order);
        verify(inventoryClient).reserve(order.getSku(), order.getQuantity(), order.getId());
    }

    @Test
    void throwErrorTest() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        doThrow(new InsufficientInventoryException("Ошибка отправки"))
                .when(inventoryClient)
                .reserve(order.getSku(), order.getQuantity(), order.getId());

        assertThrows(InsufficientInventoryException.class, () -> orderService.retry(order.getId()));

        assertEquals(OrderStatus.REJECTED, order.getStatus());
        verify(orderRepository).save(order);
        verify(inventoryClient).reserve(order.getSku(), order.getQuantity(), order.getId());

    }

    @Test
    void notFoundTest() {

        when(orderRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.retry(999L));

        verify(orderRepository).findById(999L);

    }

    @Test
    void statusConflictTest() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.CONFIRMED);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        assertThrows(OrderStatusConflictException.class, () -> orderService.retry(order.getId()));

        verify(orderRepository).findById(order.getId());

    }


    @Test
    void noSaveTest() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        doThrow(new InventoryUnavailableException("Сервис остатков временно недоступен", new RuntimeException("test cause")))
                .when(inventoryClient)
                .reserve(order.getSku(), order.getQuantity(), order.getId());

        assertThrows(InventoryUnavailableException.class, () -> orderService.retry(order.getId()));

        verify(orderRepository).findById(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(orderRepository, never()).save(order);

    }

    @Test
    void   rejectedTest() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        doThrow(new InventoryItemNotFoundException("Складская позиция со sku=" + order.getSku() + " не найдена"))
                .when(inventoryClient)
                .reserve(order.getSku(), order.getQuantity(), order.getId());

        assertThrows(InventoryItemNotFoundException.class, () -> orderService.retry(order.getId()));

        verify(orderRepository).findById(order.getId());
        assertEquals(OrderStatus.REJECTED, order.getStatus());
        verify(orderRepository).save(order);
        verify(inventoryClient).reserve(order.getSku(), order.getQuantity(), order.getId());
    }

    @Test
    void  conflictExceptionTest() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        doThrow(new InventoryReservationConflictException("Конфликт резервирования"))
                .when(inventoryClient)
                .reserve(order.getSku(), order.getQuantity(), order.getId());

        assertThrows(InventoryReservationConflictException.class, () -> orderService.retry(order.getId()));

        verify(orderRepository).findById(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(orderRepository, never()).save(order);

    }

    @Test
    void  integrationExceptionTest() {
        Order order  = new Order();
        order.setId(1L);
        order.setSku("IPHONE-15-BLACK");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        doThrow(new InventoryIntegrationException("Просто ошибка"))
                .when(inventoryClient)
                .reserve(order.getSku(), order.getQuantity(), order.getId());

        assertThrows(InventoryIntegrationException.class, () -> orderService.retry(order.getId()));

        verify(orderRepository).findById(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(orderRepository, never()).save(order);

    }

}
