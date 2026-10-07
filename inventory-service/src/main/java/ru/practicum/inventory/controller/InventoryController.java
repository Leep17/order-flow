package ru.practicum.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.inventory.dto.InventoryItemDto;
import ru.practicum.inventory.mapper.InventoryItemMapper;
import ru.practicum.inventory.service.InventoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public InventoryItemDto getById(@PathVariable Long id) {
        return InventoryItemMapper.toInventoryItemDto(inventoryService.getById(id));
    }
}
