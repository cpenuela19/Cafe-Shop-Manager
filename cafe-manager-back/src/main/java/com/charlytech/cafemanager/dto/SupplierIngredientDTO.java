package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.MeasurementUnit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierIngredientDTO extends BaseDTO {
    private double unitPrice;
    private int baseQuantity;
    private MeasurementUnit baseUnit;
    private boolean active;
    private LocalDateTime createdAt;

    private IngredientDTO  ingredient;
    private SupplierDTO supplier;
}
