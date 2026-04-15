package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
