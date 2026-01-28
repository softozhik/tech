package com.yourname.equipment.user.service;

import com.yourname.equipment.common.exception.BusinessException;
import com.yourname.equipment.common.exception.ResourceNotFoundException;
import com.yourname.equipment.company.entity.Company;
import com.yourname.equipment.company.repository.CompanyRepository;
import com.yourname.equipment.department.entity.Department;
import com.yourname.equipment.department.repository.DepartmentRepository;
import com.yourname.equipment.user.dto.CreateUserDto;
import com.yourname.equipment.user.dto.UpdateUserDto;
import com.yourname.equipment.user.dto.UserResponseDto;
import com.yourname.equipment.user.entity.User;
import com.yourname.equipment.user.mapper.UserMapper;
import com.yourname.equipment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с пользователями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создать нового пользователя.
     */
    public UserResponseDto create(CreateUserDto dto) {
        log.debug("Создание пользователя: {}", dto.getEmail());
        
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Пользователь с email '" + dto.getEmail() + "' уже существует");
        }

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));

        User user = userMapper.toEntity(dto);
        user.setCompany(company);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Отдел", dto.getDepartmentId()));
            user.setDepartment(department);
        }

        if (user.getStatus() == null) {
            user.setStatus(User.UserStatus.ACTIVE);
        }

        User saved = userRepository.save(user);
        log.info("Пользователь создан: id={}, email={}", saved.getId(), saved.getEmail());
        
        return userMapper.toDto(saved);
    }

    /**
     * Получить пользователя по ID.
     */
    @Transactional(readOnly = true)
    public UserResponseDto getById(Long id) {
        log.debug("Получение пользователя: id={}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь", id));
        
        return userMapper.toDto(user);
    }

    /**
     * Получить всех пользователей компании с пагинацией.
     */
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getByCompanyId(Long companyId, Pageable pageable) {
        log.debug("Получение пользователей компании: companyId={}", companyId);
        
        return userRepository.findByCompanyId(companyId, pageable)
                .map(userMapper::toDto);
    }

    /**
     * Обновить пользователя.
     */
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        log.debug("Обновление пользователя: id={}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь", id));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new BusinessException("Пользователь с email '" + dto.getEmail() + "' уже существует");
            }
        }

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));
            user.setCompany(company);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Отдел", dto.getDepartmentId()));
            user.setDepartment(department);
        }

        if (dto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        userMapper.updateEntity(user, dto);
        User saved = userRepository.save(user);
        log.info("Пользователь обновлен: id={}", saved.getId());
        
        return userMapper.toDto(saved);
    }

    /**
     * Удалить пользователя.
     */
    public void delete(Long id) {
        log.debug("Удаление пользователя: id={}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь", id));
        
        userRepository.delete(user);
        log.info("Пользователь удален: id={}", id);
    }
}
