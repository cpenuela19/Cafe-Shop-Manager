package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ProductEntity extends BaseEntity {

    private String Name;
    private String sku;
    private double suggestedPrice;
    private boolean active;
    private LocalDateTime createdAt;

    @ManyToOne
    private RecipeEntity recipe;
}
