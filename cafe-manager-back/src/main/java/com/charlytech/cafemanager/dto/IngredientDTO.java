package com.charlytech.cafemanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngredientDTO extends BaseDTO{
    private String name;
    private boolean active;
    private LocalDateTime createdAt;
}
