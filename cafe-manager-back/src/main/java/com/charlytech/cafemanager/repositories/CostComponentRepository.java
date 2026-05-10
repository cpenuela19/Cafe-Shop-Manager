package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.CostComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostComponentRepository extends JpaRepository<CostComponentEntity, Long>{
    boolean existsByStaffRoleId(Long staffRoleId);
}
