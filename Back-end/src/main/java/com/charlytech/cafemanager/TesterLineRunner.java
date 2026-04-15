package com.charlytech.cafemanager;
import com.charlytech.cafemanager.entities.IngredientEntity;
import com.charlytech.cafemanager.entities.SupplierEntity;
import com.charlytech.cafemanager.entities.SupplierIngredientEntity;
import com.charlytech.cafemanager.repositories.IngredientRepository;
import com.charlytech.cafemanager.repositories.SupplierIngredientRepository;
import com.charlytech.cafemanager.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.charlytech.cafemanager.entities.MeasurementUnit.*;

@Slf4j
@Component
public class TesterLineRunner implements CommandLineRunner {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierIngredientRepository supplierIngredientRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Create and save suppliers
        SupplierEntity supplier1 = new SupplierEntity();
        supplier1.setName("Insumos COLMAX");
        supplier1.setPhoneNumber("3012345678");
        supplier1.setEmail("contacto@cafeimperial.com");
        supplier1.setAddress("Calle 72 #10-34, Bogotá");
        supplierRepository.save(supplier1);

        SupplierEntity supplier2 = new SupplierEntity();
        supplier2.setName("Distribuidora El Grano");
        supplier2.setPhoneNumber("3109876543");
        supplier2.setEmail("ventas@elgrano.com");
        supplier2.setAddress("Carrera 15 #85-20, Bogotá");
        supplierRepository.save(supplier2);

        // 2. Create and save ingredients
        IngredientEntity ingredient1 = new IngredientEntity();
        ingredient1.setName("Sal Marina");
        ingredientRepository.save(ingredient1);

        IngredientEntity ingredient2 = new IngredientEntity();
        ingredient2.setName("Leche Entera");
        ingredientRepository.save(ingredient2);


        // 3. Asociacion SupplierIngredient
        SupplierIngredientEntity supplierIngredient1 = new SupplierIngredientEntity();
        supplierIngredient1.setUnitPrice(30.000);
        supplierIngredient1.setBaseQuantity(1);
        supplierIngredient1.setBaseUnit(KILOGRAM);
        supplierIngredient1.setActive(true);
        supplierIngredient1.setCreatedAt(LocalDateTime.now());
        supplierIngredient1.setSupplier(supplier1);
        supplierIngredient1.setIngredient(ingredient1);
        supplierIngredientRepository.save(supplierIngredient1);


        SupplierIngredientEntity supplierIngredient2 = new SupplierIngredientEntity();
        supplierIngredient2.setUnitPrice(12.000);
        supplierIngredient2.setBaseQuantity(1);
        supplierIngredient2.setBaseUnit(LITER);
        supplierIngredient2.setActive(true);
        supplierIngredient2.setCreatedAt(LocalDateTime.now());
        supplierIngredient2.setSupplier(supplier1);
        supplierIngredient2.setIngredient(ingredient2);
        supplierIngredientRepository.save(supplierIngredient2);


        System.out.println("!--------------------- FROM SUPPLIER VIEW! ---------------------!");

        List<SupplierIngredientEntity> rel = new ArrayList<>();
        rel.add(supplierIngredient1);
        rel.add(supplierIngredient2);

        supplier1.setSupplierIngredient(rel);
        supplierRepository.save(supplier1);

// PRINT DETALLADO SUPPLIER
        System.out.println("Supplier:");
        System.out.println("  id: " + supplier1.getId());
        System.out.println("  name: " + supplier1.getName());
        System.out.println("  phoneNumber: " + supplier1.getPhoneNumber());
        System.out.println("  email: " + supplier1.getEmail());
        System.out.println("  address: " + supplier1.getAddress());
        System.out.println("  ingredients:");

        for (SupplierIngredientEntity si : supplier1.getSupplierIngredient()) {
            System.out.println("    - SupplierIngredient:");
            System.out.println("        id: " + si.getId());
            System.out.println("        unitPrice: " + si.getUnitPrice());
            System.out.println("        baseQuantity: " + si.getBaseQuantity());
            System.out.println("        baseUnit: " + si.getBaseUnit());
            System.out.println("        active: " + si.isActive());
            System.out.println("        createdAt: " + si.getCreatedAt());
            System.out.println("        ingredient:");
            System.out.println("            id: " + si.getIngredient().getId());
            System.out.println("            name: " + si.getIngredient().getName());
        }

        System.out.println("!--------------------- FROM SUPPLIERINGREDIENT VIEW! ---------------------!");

// PRINT DETALLADO SUPPLIER INGREDIENT 1
        System.out.println("SupplierIngredient 1:");
        System.out.println("  id: " + supplierIngredient1.getId());
        System.out.println("  unitPrice: " + supplierIngredient1.getUnitPrice());
        System.out.println("  baseQuantity: " + supplierIngredient1.getBaseQuantity());
        System.out.println("  baseUnit: " + supplierIngredient1.getBaseUnit());
        System.out.println("  active: " + supplierIngredient1.isActive());
        System.out.println("  createdAt: " + supplierIngredient1.getCreatedAt());

        System.out.println("  supplier:");
        System.out.println("    id: " + supplierIngredient1.getSupplier().getId());
        System.out.println("    name: " + supplierIngredient1.getSupplier().getName());
        System.out.println("    phoneNumber: " + supplierIngredient1.getSupplier().getPhoneNumber());
        System.out.println("    email: " + supplierIngredient1.getSupplier().getEmail());
        System.out.println("    address: " + supplierIngredient1.getSupplier().getAddress());

        System.out.println("  ingredient:");
        System.out.println("    id: " + supplierIngredient1.getIngredient().getId());
        System.out.println("    name: " + supplierIngredient1.getIngredient().getName());


// PRINT DETALLADO SUPPLIER INGREDIENT 2
        System.out.println("SupplierIngredient 2:");
        System.out.println("  id: " + supplierIngredient2.getId());
        System.out.println("  unitPrice: " + supplierIngredient2.getUnitPrice());
        System.out.println("  baseQuantity: " + supplierIngredient2.getBaseQuantity());
        System.out.println("  baseUnit: " + supplierIngredient2.getBaseUnit());
        System.out.println("  active: " + supplierIngredient2.isActive());
        System.out.println("  createdAt: " + supplierIngredient2.getCreatedAt());

        System.out.println("  supplier:");
        System.out.println("    id: " + supplierIngredient2.getSupplier().getId());
        System.out.println("    name: " + supplierIngredient2.getSupplier().getName());
        System.out.println("    phoneNumber: " + supplierIngredient2.getSupplier().getPhoneNumber());
        System.out.println("    email: " + supplierIngredient2.getSupplier().getEmail());
        System.out.println("    address: " + supplierIngredient2.getSupplier().getAddress());

        System.out.println("  ingredient:");
        System.out.println("    id: " + supplierIngredient2.getIngredient().getId());
        System.out.println("    name: " + supplierIngredient2.getIngredient().getName());
        System.out.println("    name: " + supplierIngredient2.getIngredient().isActive())   ;


    }
}
