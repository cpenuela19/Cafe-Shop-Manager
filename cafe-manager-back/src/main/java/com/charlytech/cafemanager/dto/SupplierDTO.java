package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierDTO extends BaseDTO{
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean active;
    private LocalDateTime createdAt;
}
