package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.StaffRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRoleRepository extends JpaRepository<StaffRoleEntity, Long> {
    boolean existsByName(String name);
}
