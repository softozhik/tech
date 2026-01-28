package com.yourname.equipment.department.repository;

import com.yourname.equipment.department.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с отделами.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Page<Department> findByCompanyId(Long companyId, Pageable pageable);

    List<Department> findByCompanyId(Long companyId);

    List<Department> findByParentDepartmentId(Long parentDepartmentId);
}
