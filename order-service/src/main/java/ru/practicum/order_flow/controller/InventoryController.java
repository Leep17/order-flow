package ru.practicum.order_flow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.order_flow.client.InventoryClient;
import ru.practicum.order_flow.dto.ReserveInventoryRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryClient inventoryClient;

    @GetMapping("/ping")
    public String ping() {
        return inventoryClient.ping();
    }

    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reserve(@Valid @RequestBody ReserveInventoryRequestDto reserveInventoryRequestDto) {
        inventoryClient.reserve(reserveInventoryRequestDto.getSku(), reserveInventoryRequestDto.getQuantity(), reserveInventoryRequestDto.getOrderId());
    }

}
