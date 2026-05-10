package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.SupplierEntity;
import com.charlytech.cafemanager.entities.SupplierIngredientEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.SupplierIngredientRepository;
import com.charlytech.cafemanager.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierIngredientRepository supplierIngredientRepository;

    @Transactional
    public SupplierEntity createSupplier(@NonNull SupplierEntity supplierEntity) {
        if (supplierRepository.existsByName(supplierEntity.getName()))
            throw new IllegalOperationException("That name is already taken, Try Log in instead");
        if (supplierRepository.existsByEmail(supplierEntity.getEmail()))
            throw new IllegalOperationException("That Email Address is already taken, Try Log in instead");
        // Phone number must not be blank
        if (supplierEntity.getPhoneNumber() == null || supplierEntity.getPhoneNumber().isBlank())
            throw new IllegalOperationException("Supplier phone number cannot be blank");
        return supplierRepository.save(supplierEntity);
    }

    // A supplier cannot be deactivated if it is the only supplier providing a specific ingredient
    @Transactional
    public SupplierEntity desactivateSupplier(@NonNull SupplierEntity supplierEntity) {
        if (!supplierEntity.isActive())
            throw new IllegalOperationException("Supplier is not active, dumbass");
        for (SupplierIngredientEntity ingredientList : supplierEntity.getSupplierIngredient()) {
            Long a = ingredientList.getIngredient().getId();
            int count = supplierIngredientRepository.countByIngredientId(a);
            if (count == 0)
                throw new IllegalOperationException("This supplier doesn't have ingredients");
            else if (count == 1)
                throw new IllegalOperationException("This supplier is the only one who is supplying ingredient: " + ingredientList.getIngredient().getName());
        }
        supplierEntity.setActive(false);
        return supplierRepository.save(supplierEntity);
    }

    // Cannot delete a supplier that still has ingredient associations
    @Transactional
    public void deleteSupplier(@NonNull SupplierEntity supplierEntity) {
        if (!supplierEntity.getSupplierIngredient().isEmpty())
            throw new IllegalOperationException("Cannot delete supplier '" + supplierEntity.getName() +
                    "': it has " + supplierEntity.getSupplierIngredient().size() +
                    " ingredient association(s). Remove them first.");
        supplierRepository.delete(supplierEntity);
    }

    // Cannot reactivate a supplier if none of its associated ingredients are active
    @Transactional
    public SupplierEntity reactivateSupplier(@NonNull SupplierEntity supplierEntity) {
        if (supplierEntity.isActive())
            throw new IllegalOperationException("Supplier is already active");
        boolean hasActiveIngredient = supplierEntity.getSupplierIngredient().stream()
                .anyMatch(si -> si.getIngredient().isActive());
        if (!supplierEntity.getSupplierIngredient().isEmpty() && !hasActiveIngredient)
            throw new IllegalOperationException("Cannot reactivate supplier '" + supplierEntity.getName() +
                    "': none of its associated ingredients are currently active");
        supplierEntity.setActive(true);
        return supplierRepository.save(supplierEntity);
    }
}
