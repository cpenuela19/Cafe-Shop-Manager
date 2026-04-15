package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
public class SupplierIngredientEntity extends BaseEntity {

    private double unitPrice;
    private int baseQuantity;
    private MeasurementUnit baseUnit;
    private boolean active;
    private LocalDateTime createdAt;

    @ManyToOne
    private IngredientEntity ingredient;

    @ToString.Exclude
    @ManyToOne
    private SupplierEntity supplier;

}
