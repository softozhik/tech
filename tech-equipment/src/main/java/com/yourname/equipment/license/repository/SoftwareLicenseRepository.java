package com.yourname.equipment.license.repository;

import com.yourname.equipment.license.entity.SoftwareLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для работы с лицензиями на ПО.
 */
@Repository
public interface SoftwareLicenseRepository extends JpaRepository<SoftwareLicense, Long> {

    Page<SoftwareLicense> findByCompanyId(Long companyId, Pageable pageable);

    @Query("SELECT l FROM SoftwareLicense l WHERE l.company.id = :companyId " +
           "AND l.expiryDate IS NOT NULL AND l.expiryDate <= :date")
    List<SoftwareLicense> findExpiringLicenses(@Param("companyId") Long companyId, @Param("date") LocalDate date);
}
