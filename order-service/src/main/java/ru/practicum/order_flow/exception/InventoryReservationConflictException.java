package ru.practicum.order_flow.exception;

public class InventoryReservationConflictException extends RuntimeException {
    public InventoryReservationConflictException(String message) {
        super(message);
    }
}
