package com.charlytech.cafemanager.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SupplierDetailDTO extends SupplierDTO {
    private List<SupplierIngredientDTO> ingredients = new ArrayList<>();
}
