package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.IngredientPriceHistoryEntity;
import com.charlytech.cafemanager.entities.UserEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.IngredientPriceHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class IngredientPriceHistoryService {

    @Autowired
    private IngredientPriceHistoryRepository priceHistoryRepository;

    // Only ADMIN or MANAGER can record price changes;
    // new price must be > 0 and must differ from the previous price
    @Transactional
    public IngredientPriceHistoryEntity recordPriceChange(IngredientPriceHistoryEntity priceHistory) {
        UserEntity registeredBy = priceHistory.getUser();
        if (registeredBy == null
                || (registeredBy.getRole() != UserEntity.Role.ADMIN
                    && registeredBy.getRole() != UserEntity.Role.MANAGER))
            throw new IllegalOperationException("Only ADMIN or MANAGER users can register ingredient price changes");

        if (priceHistory.getActualPrice() <= 0)
            throw new IllegalOperationException("New ingredient price must be greater than 0");

        if (Double.compare(priceHistory.getActualPrice(), priceHistory.getPreviousPrice()) == 0)
            throw new IllegalOperationException("New price is identical to the current price. No change recorded.");

        priceHistory.setChangedAt(LocalDateTime.now());
        return priceHistoryRepository.save(priceHistory);
    }
}
