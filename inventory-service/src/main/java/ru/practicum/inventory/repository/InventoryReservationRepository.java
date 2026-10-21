package ru.practicum.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.inventory.reservation.InventoryReservation;

import java.util.Optional;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, Long> {
    Optional<InventoryReservation> findByOrderId(Long orderId);
}
