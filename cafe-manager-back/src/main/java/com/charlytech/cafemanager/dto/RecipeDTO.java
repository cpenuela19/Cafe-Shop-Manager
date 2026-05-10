package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.RecipeEntity.RecipeCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecipeDTO extends BaseDTO {
    private String internalCode;
    private String name;
    private String description;
    private RecipeCategory category;
    private int prepTime;
    private int batchSize;
    private boolean active;
    private LocalDateTime createdAt;
}
