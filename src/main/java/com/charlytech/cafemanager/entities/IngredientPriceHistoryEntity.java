package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class IngredientPriceHistoryEntity extends BaseEntity {

    private double previousPrice;
    private double actualPrice;
    private Date changedAt;



    @ManyToOne
    private UserEntity userEntity;
}
