package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.entities.SupplierEntity;
import com.charlytech.cafemanager.entities.SupplierIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.SupplierIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SupplierIngredientService {

    @Autowired
    private SupplierIngredientRepository repository;

    @Transactional
    public SupplierIngredientEntity saveIngredient(@NonNull SupplierIngredientEntity supplierIngredientEntity) {
        if (supplierIngredientEntity.getUnitPrice() < 100)
            throw new IllegalOperationException("Supplier Ingredient Unit Price cannot be less than $100");

        // Base quantity must be greater than 0
        if (supplierIngredientEntity.getBaseQuantity() <= 0)
            throw new IllegalOperationException("Base quantity must be greater than 0");

        // Cannot link an inactive supplier or an inactive ingredient
        SupplierEntity supplier = supplierIngredientEntity.getSupplier();
        IngredientEntity ingredient = supplierIngredientEntity.getIngredient();

        if (supplier == null || !supplier.isActive())
            throw new IllegalOperationException("Cannot link an inactive or missing supplier");
        if (ingredient == null || !ingredient.isActive())
            throw new IllegalOperationException("Cannot link an inactive or missing ingredient");

        // Cannot create a duplicate supplier-ingredient pair
        if (repository.existsBySupplierIdAndIngredientId(supplier.getId(), ingredient.getId()))
            throw new IllegalOperationException("Supplier '" + supplier.getName() +
                    "' already supplies ingredient '" + ingredient.getName() + "'");

        return repository.save(supplierIngredientEntity);
    }

    // A supplier ingredient cannot be deleted if it is the only source for that ingredient
    @Transactional
    public void deleteSupplierIngredient(Long supplierIngredientId) {
        Optional<SupplierIngredientEntity> supplierIngredient = repository.findById(supplierIngredientId);
        if (supplierIngredient.isEmpty())
            throw new IllegalOperationException("Supplier Ingredient Not Found");
        if (repository.countByIngredientId(supplierIngredient.get().getIngredient().getId()) < 2)
            throw new IllegalOperationException("Relationship SupplierIngredient cannot be deleted because " +
                    "this supplier is the only one that provides this ingredient...");
        repository.deleteById(supplierIngredientId);
    }
}
