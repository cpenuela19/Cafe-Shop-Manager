package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUsername(String username);
    List<UserEntity> findByEmail(String email);
}
