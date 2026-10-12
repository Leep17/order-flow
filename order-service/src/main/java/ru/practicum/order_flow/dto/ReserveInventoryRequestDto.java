package ru.practicum.order_flow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveInventoryRequestDto {

    @NotBlank
    private String sku;

    @Positive
    private int quantity;
}
