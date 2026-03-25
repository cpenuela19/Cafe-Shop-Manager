package com.charlytech.cafemanager.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UserEntity extends BaseEntity {

    enum Role {
        ADMIN,
        MANAGER,
        BAKER,
        VENDOR
    }

    // Attributes
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;

    // Relationships --- Here I used fetch = FetchType.LAZY just to point it up.
    // By default, in this relationship (OneToMany from this side) is going to be loaded as LAZY.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<IngredientPriceHistoryEntity> ingredientPrices = new ArrayList<>();
}
