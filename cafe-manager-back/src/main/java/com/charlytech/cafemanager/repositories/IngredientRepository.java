package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    IngredientEntity findByName(String name);
}
