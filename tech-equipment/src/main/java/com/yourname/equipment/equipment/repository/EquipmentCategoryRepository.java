package com.yourname.equipment.equipment.repository;

import com.yourname.equipment.equipment.entity.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с категориями техники.
 */
@Repository
public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Long> {

    Optional<EquipmentCategory> findByName(String name);

    boolean existsByName(String name);
}
