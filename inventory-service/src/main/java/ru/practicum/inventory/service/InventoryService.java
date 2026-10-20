package ru.practicum.inventory.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.inventory.InventoryItem.InventoryItem;
import ru.practicum.inventory.exception.InsufficientStockException;
import ru.practicum.inventory.exception.InventoryItemNotFoundException;
import ru.practicum.inventory.exception.InventoryReservationConflictException;
import ru.practicum.inventory.repository.InventoryRepository;
import ru.practicum.inventory.repository.InventoryReservationRepository;
import ru.practicum.inventory.reservation.InventoryReservation;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryReservationRepository inventoryReservationRepository;

    public InventoryItem getById(Long id) {
            return inventoryRepository.findById(id)
                   .orElseThrow(() -> new InventoryItemNotFoundException("Предмет с id=" + id + " не найден"));
     }

     @Transactional
    public void reserve(String sku, int quantity, Long orderId) {
        InventoryItem inventoryItem = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new InventoryItemNotFoundException("Предмет со sku=" + sku + " не найден"));

        InventoryReservation inventoryReservation = inventoryReservationRepository.findByOrderId(orderId)
                .orElse(null);

        if (inventoryReservation == null) {
            if (inventoryItem.getQuantity() >= quantity) {
                inventoryItem.setQuantity(inventoryItem.getQuantity() - quantity);

                InventoryReservation inventoryReservationNew = new InventoryReservation();
                inventoryReservationNew.setSku(sku);
                inventoryReservationNew.setQuantity(quantity);
                inventoryReservationNew.setOrderId(orderId);
                inventoryReservationNew.setCreatedAt(LocalDateTime.now());
                inventoryReservationRepository.save(inventoryReservationNew);
            } else {
                throw new InsufficientStockException("Складская позиция существует, но запрошенное количество зарезервировать невозможно.");
            }
        } else {
            if (!Objects.equals(inventoryReservation.getSku(), sku) || inventoryReservation.getQuantity() != quantity) {
                throw new InventoryReservationConflictException("Резервирование " + orderId +" уже использовано для другого резервирования, и новый запрос конфликтует с уже сохранёнными данными.");
            }
        }
    }
}
