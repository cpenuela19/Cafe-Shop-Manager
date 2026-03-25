package com.charlytech.cafemanager.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class IngredientEntity extends BaseEntity {

    @OneToMany(mappedBy = "ingredient")
    private List<IngredientPriceHistoryEntity> ingredientPriceHistory =  new ArrayList<>();

}
