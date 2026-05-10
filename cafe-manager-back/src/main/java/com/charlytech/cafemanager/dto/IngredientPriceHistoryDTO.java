package com.charlytech.cafemanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngredientPriceHistoryDTO extends BaseDTO {
    private double previousPrice;
    private double actualPrice;
    private LocalDateTime changedAt;
    private IngredientDTO ingredient;
    private UserResponseDTO registeredBy;
}
