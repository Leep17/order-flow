package ru.practicum.order_flow.exception;

public class OrderStatusConflictException extends RuntimeException {
    public OrderStatusConflictException(String message) {
        super(message);
    }
}
