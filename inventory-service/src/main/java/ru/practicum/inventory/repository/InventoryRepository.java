package ru.practicum.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.inventory.InventoryItem.InventoryItem;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
}
