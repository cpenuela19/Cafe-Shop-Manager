package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.entities.MeasurementUnit;
import com.charlytech.cafemanager.entities.RecipeEntity;
import com.charlytech.cafemanager.entities.RecipeIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import com.charlytech.cafemanager.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

//    No duplicate internal code
    @Transactional
    public RecipeEntity createRecipe(RecipeEntity recipeEntity) {

        if(recipeRepository.existsByInternalCode(recipeEntity.getInternalCode()))
            throw new IllegalOperationException("Internal code already exists");

        return recipeRepository.save(recipeEntity);
    }

//    A recipe cannot be activated if any of its ingredients are inactive
    @Transactional
    public RecipeEntity updateRecipe(RecipeEntity recipeEntity) {
        for (RecipeIngredientEntity rel: recipeEntity.getRecipeIngredients()) {
            if(!rel.getIngredient().isActive())
                throw new IllegalOperationException("Ingredient: "+rel.getIngredient().getName()+ " is not active");
        }
        recipeEntity.setActive(true);
        return  recipeRepository.save(recipeEntity);
    }

//    //    Cannot add an ingredient that doesn't exist
//    @Transactional
//    public RecipeIngredientEntity addIngredient(Long recipeId, Long ingredientId, double quantity, MeasurementUnit  unit) {
//
//        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(recipeId);
//        Optional<IngredientEntity> ingredientRel =  ingredientRepository.findById(ingredientId);
//
//        if (ingredientRel.isEmpty())
//            throw new IllegalOperationException("Ingredient not found");
//        if (recipeEntity.isEmpty())
//            throw new IllegalOperationException("Recipe not found");
//
//        RecipeIngredientEntity rel = new  RecipeIngredientEntity();
//        rel.setQuantityUsed(quantity);
//        rel.setMeasurementUnit(unit);
//        rel.setIngredient(ingredientRel.get());
//        rel.setRecipe(recipeEntity.get());
//
//        return recipeIngredientRepository.save(rel);
//    }
}
