package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class RecipeEntity extends BaseEntity {

    public enum RecipeCategory{
      BREAD,
      PASTRY, // Hojaldres
      BAKERY,
      CHOCOLATE,
      COOKIE,
      OTHER
    }

    private String internalCode;
    private String name;
    private String description;
    private RecipeCategory category;
    private int prepTime; //Estimated preparation time (in minutes)
    private int batchSize; // Number of product output per batch xd
    private boolean active = false;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "recipe")
    private List<ProductEntity> products = new ArrayList<>();
    @OneToMany(mappedBy = "recipe")
    private List<CostComponentEntity> costComponents =  new ArrayList<>();
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredientEntity> recipeIngredients = new ArrayList<>();

}
