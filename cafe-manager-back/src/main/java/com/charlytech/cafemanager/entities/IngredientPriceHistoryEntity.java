package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class IngredientPriceHistoryEntity extends BaseEntity {

    private double previousPrice;
    private double actualPrice;
    private LocalDateTime changedAt;

    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private IngredientEntity ingredient;

}
