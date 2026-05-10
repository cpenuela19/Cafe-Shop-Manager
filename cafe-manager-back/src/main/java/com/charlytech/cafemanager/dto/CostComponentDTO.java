package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.CostComponentEntity.CostComponentType;
import lombok.Data;

@Data
public class CostComponentDTO extends BaseDTO {
    private CostComponentType type;
    private String description;
    private int timeSpentByStaff;
    private double overheadAmount;
    private StaffRoleDTO staffRole;
}
