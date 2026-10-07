package ru.practicum.inventory.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.inventory.InventoryItem.InventoryItem;
import ru.practicum.inventory.exception.InsufficientStockException;
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

     @Transactional
    public void reserve(String sku, int quantity) {
        InventoryItem inventoryItem = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new InventoryItemNotFoundException("Предмет со sku=" + sku + " не найден"));

        if (inventoryItem.getQuantity()>= quantity) {
            inventoryItem.setQuantity(inventoryItem.getQuantity()-quantity);
        } else {
            throw new InsufficientStockException("Складская позиция существует, но запрошенное количество зарезервировать невозможно.");
        }
    }
}
