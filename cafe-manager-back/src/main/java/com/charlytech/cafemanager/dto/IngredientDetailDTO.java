package com.charlytech.cafemanager.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IngredientDetailDTO extends IngredientDTO {
    private List<SupplierIngredientDTO> suppliersDTO =  new ArrayList<>();
}
