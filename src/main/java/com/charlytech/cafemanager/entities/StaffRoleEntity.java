package com.charlytech.cafemanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class StaffRoleEntity extends BaseEntity {

    private String name;
    private double pricePerHour;

    @OneToMany(mappedBy = "staffRole")
    private List<CostComponentEntity> costComponents = new ArrayList<>() ;

}
