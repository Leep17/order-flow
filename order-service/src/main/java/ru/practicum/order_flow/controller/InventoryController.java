package ru.practicum.order_flow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.order_flow.client.InventoryClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryClient inventoryClient;

    @GetMapping("/ping")
    public String ping() {
        return inventoryClient.ping();
    }


}
