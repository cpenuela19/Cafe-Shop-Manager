package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {
    boolean existsByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
