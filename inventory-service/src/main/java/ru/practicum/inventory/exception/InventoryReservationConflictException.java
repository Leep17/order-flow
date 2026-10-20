package ru.practicum.inventory.exception;

public class InventoryReservationConflictException extends RuntimeException {
    public InventoryReservationConflictException(String message) {
        super(message);
    }
}
