package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SupplierIngredientEntity extends BaseEntity {

    enum MeasurementUnit{
        GRAM,
        KILOGRAM,
        LITER,
        MILLILITER,
        UNIT,
        TABLESPOON,
        TEASPOON
    }
    private double unitPrice;
    private int baseQuantity;
    private MeasurementUnit baseUnit;
    private boolean active;
    private LocalDateTime createdAt;

    @ManyToOne
    private IngredientEntity ingredient;
    @ManyToOne
    private SupplierEntity supplier;

}
