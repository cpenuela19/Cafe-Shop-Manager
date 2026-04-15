package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.entities.SupplierIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import com.charlytech.cafemanager.repositories.SupplierIngredientRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SupplierIngredientService {

    @Autowired
    private SupplierIngredientRepository repository;
//    @Autowired
//    private IngredientRepository ingredientRepository;

//    Unit price cannot be negative
    @Transactional
    public SupplierIngredientEntity saveIngredient(@NonNull SupplierIngredientEntity supplierIngredientEntity) {

        if(supplierIngredientEntity.getUnitPrice() < 100)
            throw new IllegalOperationException("Supplier Ingredient Unit Price cannot be less than $100");
        return repository.save(supplierIngredientEntity);
    }

//    A supplier ingredient cannot be deleted if it is the only source for that ingredient
    @Transactional
    public void deleteSupplierIngredient(Long supplierIngredientId) {
        Optional<SupplierIngredientEntity> supplierIngredient = repository.findById(supplierIngredientId);
        if(supplierIngredient.isEmpty())
            throw new IllegalOperationException("Supplier Ingredient Not Found");

        if(repository.countByIngredientId(supplierIngredient.get().getIngredient().getId()) < 2)
            throw new IllegalOperationException("Relationship SupplierIngredient cannot be deleted because " +
                    "this supplier is the only one that provides this ingredient...");

//        SECOND APPROACH THAT COULD BE IMPLEMENTED (NOT EFFICIENT)
//        IngredientEntity target = supplierIngredient.get().getIngredient();
//        if (target.getSupplierIngredient().size() < 2)
//            throw new IllegalOperationException("Relationship SupplierIngredient cannot be deleted because " +
//                    "this supplier is the only one that provides this ingredient...");

        repository.deleteById(supplierIngredientId);
    }
}
