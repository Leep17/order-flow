package ru.practicum.inventory.InventoryItem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sku", nullable = false, unique = true, length = 100)
    private String sku;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
