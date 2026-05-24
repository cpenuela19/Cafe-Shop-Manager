package com.charlytech.cafemanager.controllers;

import com.charlytech.cafemanager.dto.IngredientDTO;
import com.charlytech.cafemanager.dto.IngredientDetailDTO;
import com.charlytech.cafemanager.dto.SupplierDTO;
import com.charlytech.cafemanager.dto.SupplierIngredientDTO;
import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.exceptions.EntityNotFoundException;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import com.charlytech.cafemanager.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAll() {
        List<IngredientDTO> result = ingredientRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDetailDTO> getById(@PathVariable Long id) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id));
        return ResponseEntity.ok(toDetailDTO(ingredient));
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> create(@RequestBody IngredientDTO dto) {
        IngredientEntity entity = new IngredientEntity();
        entity.setName(dto.getName());
        IngredientEntity saved = ingredientService.createIngredient(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<IngredientDTO> deactivate(@PathVariable Long id) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id));
        return ResponseEntity.ok(toDTO(ingredientService.updateIngredient(ingredient)));
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<IngredientDTO> reactivate(@PathVariable Long id) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id));
        return ResponseEntity.ok(toDTO(ingredientService.reactivateIngredient(ingredient)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id));
        ingredientService.deleteIngredient(ingredient);
        return ResponseEntity.noContent().build();
    }

    // --- Mappers ---

    private IngredientDTO toDTO(IngredientEntity entity) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private IngredientDetailDTO toDetailDTO(IngredientEntity entity) {
        IngredientDetailDTO dto = new IngredientDetailDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());

        List<SupplierIngredientDTO> suppliers = entity.getSupplierIngredient().stream()
                .map(si -> {
                    SupplierIngredientDTO siDTO = new SupplierIngredientDTO();
                    siDTO.setId(si.getId());
                    siDTO.setUnitPrice(si.getUnitPrice());
                    siDTO.setBaseQuantity(si.getBaseQuantity());
                    siDTO.setBaseUnit(si.getBaseUnit());
                    siDTO.setActive(si.isActive());
                    siDTO.setCreatedAt(si.getCreatedAt());

                    SupplierDTO supplierDTO = new SupplierDTO();
                    supplierDTO.setId(si.getSupplier().getId());
                    supplierDTO.setName(si.getSupplier().getName());
                    supplierDTO.setPhoneNumber(si.getSupplier().getPhoneNumber());
                    supplierDTO.setEmail(si.getSupplier().getEmail());
                    supplierDTO.setActive(si.getSupplier().isActive());
                    siDTO.setSupplier(supplierDTO);

                    return siDTO;
                })
                .toList();

        dto.setSuppliersDTO(suppliers);
        return dto;
    }
}
