package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class IngredientEntity extends BaseEntity {

    private String name;
    private boolean active;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ingredient")
    private List<IngredientPriceHistoryEntity> ingredientPriceHistory =  new ArrayList<>();
    @OneToMany(mappedBy = "ingredient")
    private List<SupplierIngredientEntity>  supplierIngredient = new ArrayList<>();
    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredientEntity>  recipeIngredient = new ArrayList<>();

}
