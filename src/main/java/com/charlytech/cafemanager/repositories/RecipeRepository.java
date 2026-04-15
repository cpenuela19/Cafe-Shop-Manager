package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    boolean existsByInternalCode(String internalCode);
}
