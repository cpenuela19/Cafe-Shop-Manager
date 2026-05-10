package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.UserEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        log.info("Trying to create user {}", userEntity);
        if (!userRepository.findByUsername(userEntity.getUsername()).isEmpty())
            throw new IllegalOperationException("Username already exists!");
        if (!userRepository.findByEmail(userEntity.getEmail()).isEmpty())
            throw new IllegalOperationException("Email already exists! try to Log In");
        // Password must be at least 8 characters
        if (userEntity.getPassword() == null || userEntity.getPassword().length() < 8)
            throw new IllegalOperationException("Password must be at least 8 characters long");
        return userRepository.save(userEntity);
    }

    @Transactional
    public void deleteUser(UserEntity userEntity) {
        log.info("Trying to delete user {}", userEntity);
        if (userEntity.getRole() == UserEntity.Role.ADMIN && userRepository.findByRole(userEntity.getRole()).size() < 2)
            throw new IllegalOperationException("THERE CANNOT BE 0 ADMINS IN THE SYSTEM!");
        userRepository.delete(userEntity);
        log.info("{} Deleted...", userEntity.getUsername());
    }

    // Cannot deactivate the last ADMIN
    @Transactional
    public UserEntity deactivateUser(UserEntity userEntity) {
        if (!userEntity.isActive())
            throw new IllegalOperationException("User is already inactive");
        if (userEntity.getRole() == UserEntity.Role.ADMIN
                && userRepository.findByRole(UserEntity.Role.ADMIN).size() < 2)
            throw new IllegalOperationException("Cannot deactivate the only ADMIN in the system");
        userEntity.setActive(false);
        return userRepository.save(userEntity);
    }

    // Cannot downgrade the last ADMIN to a non-ADMIN role
    @Transactional
    public UserEntity updateRole(UserEntity userEntity, UserEntity.Role newRole) {
        if (userEntity.getRole() == UserEntity.Role.ADMIN
                && newRole != UserEntity.Role.ADMIN
                && userRepository.findByRole(UserEntity.Role.ADMIN).size() < 2)
            throw new IllegalOperationException("Cannot change role: this user is the only ADMIN in the system");
        userEntity.setRole(newRole);
        return userRepository.save(userEntity);
    }
}
