package com.charlytech.cafemanager.services;

import com.charlytech.cafemanager.entities.StaffRoleEntity;
import com.charlytech.cafemanager.exceptions.IllegalOperationException;
import com.charlytech.cafemanager.repositories.CostComponentRepository;
import com.charlytech.cafemanager.repositories.StaffRoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StaffRoleService {

    @Autowired
    private StaffRoleRepository staffRoleRepository;
    @Autowired
    private CostComponentRepository costComponentRepository;

    // Price per hour must be > 0 and no duplicate names
    @Transactional
    public StaffRoleEntity createStaffRole(StaffRoleEntity staffRole) {
        if (staffRole.getPricePerHour() <= 0)
            throw new IllegalOperationException("Price per hour must be greater than 0");
        if (staffRoleRepository.existsByName(staffRole.getName()))
            throw new IllegalOperationException("A staff role with the name '" + staffRole.getName() + "' already exists");
        return staffRoleRepository.save(staffRole);
    }

    // Cannot delete a staff role that is referenced by any cost component
    @Transactional
    public void deleteStaffRole(StaffRoleEntity staffRole) {
        if (costComponentRepository.existsByStaffRoleId(staffRole.getId()))
            throw new IllegalOperationException("Cannot delete staff role '" + staffRole.getName() +
                    "': it is referenced by one or more cost components");
        staffRoleRepository.delete(staffRole);
    }

    // Price per hour must be > 0 on update
    @Transactional
    public StaffRoleEntity updatePricePerHour(StaffRoleEntity staffRole, double newPricePerHour) {
        if (newPricePerHour <= 0)
            throw new IllegalOperationException("Price per hour must be greater than 0");
        staffRole.setPricePerHour(newPricePerHour);
        return staffRoleRepository.save(staffRole);
    }
}
