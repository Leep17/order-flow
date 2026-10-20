package ru.practicum.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.inventory.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InventoryItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleInventoryItemNotFoundException(InventoryItemNotFoundException e) {
        return new ApiError(e.getMessage(), "INVENTORY_ITEM_NOT_FOUND");
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInsufficientStockException(InsufficientStockException e) {
        return new ApiError(e.getMessage(), "INSUFFICIENT_STOCK");
    }

    @ExceptionHandler(InventoryReservationConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInventoryReservationConflictException(InventoryReservationConflictException e) {
        return new ApiError(e.getMessage(), "RESERVATION_CONFLICT");
    }

}
