package ru.practicum.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.inventory.InventoryItem.InventoryItem;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findBySku(String sku);
}
