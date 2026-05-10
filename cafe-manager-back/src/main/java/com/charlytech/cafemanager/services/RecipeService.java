package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.RecipeEntity;
import com.charlytech.cafemanager.entities.RecipeIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import com.charlytech.cafemanager.repositories.ProductRepository;
import com.charlytech.cafemanager.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public RecipeEntity createRecipe(RecipeEntity recipeEntity) {
        if (recipeRepository.existsByInternalCode(recipeEntity.getInternalCode()))
            throw new IllegalOperationException("Internal code already exists");
        // Batch size must be greater than 0
        if (recipeEntity.getBatchSize() <= 0)
            throw new IllegalOperationException("Batch size must be greater than 0");
        // Prep time must be greater than 0
        if (recipeEntity.getPrepTime() <= 0)
            throw new IllegalOperationException("Preparation time must be greater than 0 minutes");
        return recipeRepository.save(recipeEntity);
    }

    // A recipe cannot be activated if any of its ingredients are inactive
    @Transactional
    public RecipeEntity updateRecipe(RecipeEntity recipeEntity) {
        for (RecipeIngredientEntity rel : recipeEntity.getRecipeIngredients()) {
            if (!rel.getIngredient().isActive())
                throw new IllegalOperationException("Ingredient: " + rel.getIngredient().getName() + " is not active");
        }
        recipeEntity.setActive(true);
        return recipeRepository.save(recipeEntity);
    }

    // Cannot deactivate a recipe that has active products linked to it
    @Transactional
    public RecipeEntity deactivateRecipe(RecipeEntity recipeEntity) {
        if (!recipeEntity.isActive())
            throw new IllegalOperationException("Recipe is already inactive");
        if (productRepository.existsByRecipeIdAndActiveTrue(recipeEntity.getId()))
            throw new IllegalOperationException("Cannot deactivate recipe '" + recipeEntity.getName() +
                    "': it has active products associated with it. Deactivate those products first.");
        recipeEntity.setActive(false);
        return recipeRepository.save(recipeEntity);
    }
}
