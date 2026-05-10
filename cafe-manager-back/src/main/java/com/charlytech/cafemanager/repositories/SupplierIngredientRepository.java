package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.SupplierIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierIngredientRepository extends JpaRepository<SupplierIngredientEntity, Long> {
    int countByIngredientId(Long ingredientId);
    boolean existsBySupplierIdAndIngredientId(Long supplierId, Long ingredientId);
}
