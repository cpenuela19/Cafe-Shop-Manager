package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.CostComponentEntity;
import com.charlytech.cafemanager.entities.IngredientPriceHistoryEntity;
import com.charlytech.cafemanager.entities.ProductEntity;
import com.charlytech.cafemanager.entities.RecipeEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.ProductRepository;
import com.charlytech.cafemanager.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductService {
//    Product

    @Autowired
    private ProductRepository repository;
    @Autowired
    private RecipeRepository recipeRepository;

//    No duplicate SKU
    @Transactional
    public ProductEntity createProduct(ProductEntity product)
    {
        boolean target = repository.existsBySku(product.getSku());
        if(target)
            throw new IllegalOperationException("Sku already exists");
        return repository.save(product);
    }

//    A product's suggested price cannot be lower than the total calculated cost of its recipe
    private double calcularCostoReceta(RecipeEntity recipe) {

        double costoIngredientes = recipe.getRecipeIngredients().stream()
                .mapToDouble(ri -> ri.getQuantityUsed() * ri.getIngredient()
                        .getIngredientPriceHistory()
                        .stream()
                        .mapToDouble(IngredientPriceHistoryEntity::getActualPrice)
                        .max()
                        .orElse(0))
                .sum();

        double costoComponentes = recipe.getCostComponents().stream()
                .mapToDouble(cc -> cc.getType() == CostComponentEntity.CostComponentType.DIRECT_LABOR
                        ? cc.getTimeSpentByStaff() * cc.getStaffRole().getPricePerHour()
                        : cc.getOverheadAmount())
                .sum();

        return costoIngredientes + costoComponentes;
    }
    @Transactional
    public ProductEntity createProduct(ProductEntity product, Long recipeId) {

        Optional<RecipeEntity> recipeValidation = recipeRepository.findById(recipeId);
        if (recipeValidation.isEmpty())
            throw new IllegalOperationException("Recipe not found");

        RecipeEntity recipe = recipeValidation.get();

        if (product.getSuggestedPrice() < calcularCostoReceta(recipe))
            throw new IllegalOperationException("Suggested price cannot be lower than recipe cost");

        product.setRecipe(recipe);
        return repository.save(product);
    }

}
  