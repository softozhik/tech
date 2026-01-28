package com.yourname.equipment.user.repository;

import com.yourname.equipment.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findByCompanyId(Long companyId, Pageable pageable);

    Page<User> findByDepartmentId(Long departmentId, Pageable pageable);
}
