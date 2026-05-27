package com.charlytech.cafemanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO extends BaseDTO {
    private String name;
    private String sku;
    private double suggestedPrice;
    private boolean active;
    private LocalDateTime createdAt;
    private RecipeDTO recipe;
}
