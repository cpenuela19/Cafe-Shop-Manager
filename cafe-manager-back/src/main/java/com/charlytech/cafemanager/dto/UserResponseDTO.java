package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequestDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserEntity.Role role;
    private boolean active;
    private LocalDateTime createdAt;
}
