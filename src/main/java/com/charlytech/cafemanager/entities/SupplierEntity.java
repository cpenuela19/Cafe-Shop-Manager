package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SupplierEntity extends BaseEntity {

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean active;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "supplier")
    private List<SupplierIngredientEntity>  supplierIngredient =  new ArrayList<>();

}
