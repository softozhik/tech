package com.yourname.equipment.equipment.repository;

import com.yourname.equipment.equipment.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с техникой.
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    Page<Equipment> findByCompanyId(Long companyId, Pageable pageable);

    Page<Equipment> findByStatus(Equipment.EquipmentStatus status, Pageable pageable);

    Page<Equipment> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Equipment> findByCurrentUserId(Long userId, Pageable pageable);

    @Query("SELECT e FROM Equipment e WHERE e.company.id = :companyId " +
           "AND (:status IS NULL OR e.status = :status) " +
           "AND (:categoryId IS NULL OR e.category.id = :categoryId)")
    Page<Equipment> findByCompanyIdAndFilters(
            @Param("companyId") Long companyId,
            @Param("status") Equipment.EquipmentStatus status,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    List<Equipment> findByCurrentUserId(Long userId);
}
