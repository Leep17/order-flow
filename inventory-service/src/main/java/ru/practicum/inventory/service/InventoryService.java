package ru.practicum.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.inventory.InventoryItem.InventoryItem;
import ru.practicum.inventory.exception.InventoryItemNotFoundException;
import ru.practicum.inventory.repository.InventoryRepository;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

public InventoryItem getById(Long id) {
    return inventoryRepository.findById(id)
            .orElseThrow(() -> new InventoryItemNotFoundException("Предмет с id=" + id + " не найден"));
}
}
