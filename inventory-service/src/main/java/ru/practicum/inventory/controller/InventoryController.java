package ru.practicum.inventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.inventory.dto.InventoryItemDto;
import ru.practicum.inventory.dto.ReserveInventoryRequestDto;
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

    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reserve(@Valid @RequestBody ReserveInventoryRequestDto reserveInventoryRequestDto) {
        inventoryService.reserve(reserveInventoryRequestDto.getSku(), reserveInventoryRequestDto.getQuantity());
    }
}
