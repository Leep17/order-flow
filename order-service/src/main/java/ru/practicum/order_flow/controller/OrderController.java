package ru.practicum.order_flow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.order_flow.dto.NewOrderDto;
import ru.practicum.order_flow.dto.OrderDto;
import ru.practicum.order_flow.mapper.OrderMapper;
import ru.practicum.order_flow.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id) {
        return OrderMapper.toOrderDto(orderService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@Valid @RequestBody NewOrderDto newOrderDto) {
        return OrderMapper.toOrderDto(orderService.save(newOrderDto));
    }

    @PostMapping("/{id}/retry")
    public OrderDto retry(@PathVariable Long id) {
        return OrderMapper.toOrderDto(orderService.retry(id));
    }

}
