package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.IngredientPriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientPriceHistoryRepository extends JpaRepository<IngredientPriceHistoryEntity, Long> {
    boolean existsByIngredientId(Long ingredientId);
}
