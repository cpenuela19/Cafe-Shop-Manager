package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.MeasurementUnit;
import lombok.Data;

@Data
public class RecipeIngredientDTO extends BaseDTO {
    private double quantityUsed;
    private MeasurementUnit measurementUnit;
    private IngredientDTO ingredient;
}
