package com.yourname.equipment.equipment.repository;

import com.yourname.equipment.equipment.entity.EquipmentUpgrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с апгрейдами техники.
 */
@Repository
public interface EquipmentUpgradeRepository extends JpaRepository<EquipmentUpgrade, Long> {

    Page<EquipmentUpgrade> findByEquipmentId(Long equipmentId, Pageable pageable);

    List<EquipmentUpgrade> findByEquipmentIdOrderByUpgradedAtDesc(Long equipmentId);
}
