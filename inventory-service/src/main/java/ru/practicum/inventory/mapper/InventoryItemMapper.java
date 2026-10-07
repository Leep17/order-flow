package ru.practicum.inventory.mapper;

import ru.practicum.inventory.InventoryItem.InventoryItem;
import ru.practicum.inventory.dto.InventoryItemDto;

public class InventoryItemMapper {
    public static InventoryItemDto toInventoryItemDto(InventoryItem inventoryItem) {
        return new InventoryItemDto(
                inventoryItem.getId(),
                inventoryItem.getSku(),
                inventoryItem.getQuantity()
        );
    }
}
