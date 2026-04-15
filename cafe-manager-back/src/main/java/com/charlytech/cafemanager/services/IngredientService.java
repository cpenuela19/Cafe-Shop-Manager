package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.entities.RecipeIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
//    No duplicate name
    @Transactional
    public IngredientEntity createIngredient(IngredientEntity ingredientEntity) {
        String normalizedName = ingredientEntity.getName().toLowerCase().trim().replaceAll("\\s+", " ");
        if (normalizedName.isEmpty())
            throw new IllegalOperationException("Ingredient name cannot be empty");
        if(ingredientRepository.findByName(normalizedName) != null )
            throw new IllegalOperationException("Ingredient name already exists");
        return ingredientRepository.save(ingredientEntity);
    }

//    An ingredient cannot be deactivated if it is currently used in at least one active recipe
    @Transactional
    public IngredientEntity updateIngredient(IngredientEntity ingredientEntity) {

        if(ingredientEntity.getRecipeIngredient().isEmpty())
            throw new IllegalOperationException(ingredientEntity.getName()+" has no recipe associated recipe..." );

        for(RecipeIngredientEntity rel: ingredientEntity.getRecipeIngredient())
        {
            if(rel.getRecipe().isActive())
                throw new IllegalOperationException("This " + ingredientEntity.getName()+" is being used in at least one recipe...");
        }
        
        ingredientEntity.setActive(false);
        return ingredientRepository.save(ingredientEntity);
    }

}
