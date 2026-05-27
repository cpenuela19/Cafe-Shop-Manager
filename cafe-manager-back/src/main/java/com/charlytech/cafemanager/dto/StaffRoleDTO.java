package com.charlytech.cafemanager.dto;

import lombok.Data;

@Data
public class StaffRoleDTO extends BaseDTO {
    private String name;
    private double pricePerHour;
}
