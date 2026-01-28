package com.yourname.equipment.equipment.repository;

import com.yourname.equipment.equipment.entity.EquipmentRepair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с ремонтами техники.
 */
@Repository
public interface EquipmentRepairRepository extends JpaRepository<EquipmentRepair, Long> {

    Page<EquipmentRepair> findByEquipmentId(Long equipmentId, Pageable pageable);

    List<EquipmentRepair> findByEquipmentIdOrderByStartedAtDesc(Long equipmentId);

    List<EquipmentRepair> findByStatus(EquipmentRepair.RepairStatus status);
}
