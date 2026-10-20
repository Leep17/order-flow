package ru.practicum.order_flow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.order_flow.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiError handleInventoryUnavailableException(InventoryUnavailableException e) {
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler(InventoryItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleInventoryItemNotFoundException(InventoryItemNotFoundException e) {
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInsufficientInventoryException(InsufficientInventoryException e) {
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleOrderNotFoundException(OrderNotFoundException e) {
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler(InventoryReservationConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInventoryReservationConflictException(InventoryReservationConflictException e) {
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler(InventoryIntegrationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInventoryIntegrationException(InventoryIntegrationException e) {
        return new ApiError(e.getMessage());
    }


}
