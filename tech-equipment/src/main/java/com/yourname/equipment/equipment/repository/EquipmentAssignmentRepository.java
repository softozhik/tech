package com.yourname.equipment.equipment.repository;

import com.yourname.equipment.equipment.entity.EquipmentAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с назначениями техники.
 */
@Repository
public interface EquipmentAssignmentRepository extends JpaRepository<EquipmentAssignment, Long> {

    Page<EquipmentAssignment> findByEquipmentId(Long equipmentId, Pageable pageable);

    Page<EquipmentAssignment> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT a FROM EquipmentAssignment a WHERE a.equipment.id = :equipmentId AND a.returnedAt IS NULL")
    Optional<EquipmentAssignment> findActiveAssignmentByEquipmentId(@Param("equipmentId") Long equipmentId);

    @Query("SELECT a FROM EquipmentAssignment a WHERE a.equipment.id = :equipmentId ORDER BY a.assignedAt DESC")
    List<EquipmentAssignment> findAllByEquipmentIdOrderByAssignedAtDesc(@Param("equipmentId") Long equipmentId);
}
