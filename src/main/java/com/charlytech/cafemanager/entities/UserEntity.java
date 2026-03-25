package com.charlytech.cafemanager.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
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
    private Date createdAt;

    // Relationships
    @OneToMany(mappedBy = "user")
    private List<IngredientPriceHistoryEntity> ingredientPrices = new ArrayList<>();

}
