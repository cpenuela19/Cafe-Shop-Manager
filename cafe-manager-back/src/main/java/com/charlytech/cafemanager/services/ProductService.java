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

    @Autowired
    private ProductRepository repository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    public ProductEntity createProduct(ProductEntity product) {
        if (repository.existsBySku(product.getSku()))
            throw new IllegalOperationException("Sku already exists");
        return repository.save(product);
    }

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
    public ProductEntity createProduct(@NonNull ProductEntity product, Long recipeId) {
        Optional<RecipeEntity> recipeValidation = recipeRepository.findById(recipeId);
        if (recipeValidation.isEmpty())
            throw new IllegalOperationException("Recipe not found");

        RecipeEntity recipe = recipeValidation.get();

        // Recipe must be active to create a product
        if (!recipe.isActive())
            throw new IllegalOperationException("Cannot create product: the associated recipe '" +
                    recipe.getName() + "' is not active");

        // SKU must be at least 3 characters
        if (product.getSku() == null || product.getSku().trim().length() < 3)
            throw new IllegalOperationException("SKU must be at least 3 characters long");

        // Product name cannot be blank
        if (product.getName() == null || product.getName().isBlank())
            throw new IllegalOperationException("Product name cannot be blank");

        if (product.getSuggestedPrice() < calcularCostoReceta(recipe))
            throw new IllegalOperationException("Suggested price cannot be lower than recipe cost");

        product.setRecipe(recipe);
        return repository.save(product);
    }
}
