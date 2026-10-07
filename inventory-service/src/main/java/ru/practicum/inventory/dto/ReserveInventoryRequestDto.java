package ru.practicum.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveInventoryRequestDto {
    @NotBlank
    @Size(max = 100)
    private String sku;

    @Positive
    private int quantity;
}
