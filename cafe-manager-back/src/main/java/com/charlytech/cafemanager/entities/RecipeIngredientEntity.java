package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class RecipeIngredientEntity extends BaseEntity {
    private double quantityUsed;
    private MeasurementUnit measurementUnit;

    @ManyToOne
    private IngredientEntity ingredient;
    @ManyToOne
    private RecipeEntity recipe;
}
