package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.CostComponentEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.CostComponentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CostComponentService {

    @Autowired
    private CostComponentRepository costComponentRepository;

    // Cannot add a cost component to an inactive recipe;
    // DIRECT_LABOR requires timeSpentByStaff > 0;
    // OVERHEAD and PACKING require overheadAmount > 0
    @Transactional
    public CostComponentEntity saveCostComponent(CostComponentEntity costComponent) {
        if (costComponent.getRecipe() == null || !costComponent.getRecipe().isActive())
            throw new IllegalOperationException("Cannot add a cost component to an inactive or missing recipe");

        if (costComponent.getType() == CostComponentEntity.CostComponentType.DIRECT_LABOR) {
            if (costComponent.getTimeSpentByStaff() <= 0)
                throw new IllegalOperationException("Time spent by staff must be greater than 0 for DIRECT_LABOR cost components");
        } else {
            if (costComponent.getOverheadAmount() <= 0)
                throw new IllegalOperationException("Overhead amount must be greater than 0 for " +
                        costComponent.getType() + " cost components");
        }

        return costComponentRepository.save(costComponent);
    }
}
