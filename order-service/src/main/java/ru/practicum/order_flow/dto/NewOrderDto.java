package ru.practicum.order_flow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDto {

    @NotBlank
    @Size(max = 100)
    private String sku;

    @Positive
    private int quantity;

}
