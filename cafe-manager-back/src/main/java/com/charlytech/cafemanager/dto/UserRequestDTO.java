package com.charlytech.cafemanager.dto;

import com.charlytech.cafemanager.entities.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequestDTO extends BaseDTO {
    //Create/update a User...
    private String username;
    private String email;
    private String password;
    private UserEntity.Role role;
}
