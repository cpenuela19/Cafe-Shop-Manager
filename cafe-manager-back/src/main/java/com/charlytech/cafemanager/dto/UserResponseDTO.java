package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO extends BaseDTO{
    //Consult a User...
    private String username;
    private String email;
    private UserEntity.Role role;
    private boolean active;
    private LocalDateTime createdAt;
}
