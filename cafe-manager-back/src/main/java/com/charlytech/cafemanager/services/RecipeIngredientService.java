package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.entities.MeasurementUnit;
import com.charlytech.cafemanager.entities.RecipeEntity;
import com.charlytech.cafemanager.entities.RecipeIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import com.charlytech.cafemanager.repositories.RecipeIngredientRepository;
import com.charlytech.cafemanager.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RecipeIngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Transactional
    public RecipeIngredientEntity addIngredientToRecipe(double quantityUsed, MeasurementUnit measurementUnit, Long ingredientId, Long recipeId) {
        Optional<IngredientEntity> ingredientValidation = ingredientRepository.findById(ingredientId);
        Optional<RecipeEntity> recipeValidation = recipeRepository.findById(recipeId);

        if (ingredientValidation.isEmpty())
            throw new IllegalOperationException("Ingredient not found");
        if (recipeValidation.isEmpty())
            throw new IllegalOperationException("Recipe not found");

        // Quantity used must be greater than 0
        if (quantityUsed <= 0)
            throw new IllegalOperationException("Quantity used must be greater than 0");

        // Cannot add an inactive ingredient to a recipe
        if (!ingredientValidation.get().isActive())
            throw new IllegalOperationException("Cannot add ingredient '" +
                    ingredientValidation.get().getName() + "': it is inactive");

        // Cannot add the same ingredient twice to the same recipe
        if (recipeIngredientRepository.existsByRecipeIdAndIngredientId(recipeId, ingredientId))
            throw new IllegalOperationException("Ingredient '" +
                    ingredientValidation.get().getName() + "' is already part of this recipe");

        RecipeIngredientEntity rel = new RecipeIngredientEntity();
        rel.setQuantityUsed(quantityUsed);
        rel.setMeasurementUnit(measurementUnit);
        rel.setIngredient(ingredientValidation.get());
        rel.setRecipe(recipeValidation.get());

        return recipeIngredientRepository.save(rel);
    }
}
