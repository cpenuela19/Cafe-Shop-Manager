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

    // Spring will hand me the userRepository **instance** I need here (injection).
    // I don't create or manage that object — Spring does.
    @Autowired
    private UserRepository userRepository;

    //It can NOT be two users with the same username
    //@Transactional is needed to ensure all DB operations in this method
    //success together or roll back together if something fails
    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        log.info("Trying to create user {}", userEntity);
        if (!userRepository.findByUsername(userEntity.getUsername()).isEmpty())
            throw new IllegalOperationException("Username already exists!");
        if(!userRepository.findByEmail(userEntity.getEmail()).isEmpty())
            throw new IllegalOperationException("Email already exists! try to Log In");
        return userRepository.save(userEntity);
    }
}