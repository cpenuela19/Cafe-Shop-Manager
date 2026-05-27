package com.charlytech.cafemanager.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeDetailDTO extends RecipeDTO {
    private List<RecipeIngredientDTO> ingredients = new ArrayList<>();
    private List<CostComponentDTO> costComponents = new ArrayList<>();
    private List<ProductDTO> products = new ArrayList<>();
}
