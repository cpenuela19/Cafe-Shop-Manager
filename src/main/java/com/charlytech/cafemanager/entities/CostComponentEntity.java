package com.charlytech.cafemanager.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class CostComponentEntity extends BaseEntity {

    enum CostComponentType{
        DIRECT_LABOR,
        OVERHEAD,
        PACKING
    }
    private CostComponentType type;
    private String description;
    private int timeSpentByStaff;
    private double overheadAmount;

    @ManyToOne
    private StaffRoleEntity staffRole;
    @ManyToOne
    private RecipeEntity recipe;

}
