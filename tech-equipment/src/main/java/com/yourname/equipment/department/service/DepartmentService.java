package com.yourname.equipment.department.service;

import com.yourname.equipment.common.exception.ResourceNotFoundException;
import com.yourname.equipment.company.entity.Company;
import com.yourname.equipment.company.repository.CompanyRepository;
import com.yourname.equipment.department.dto.CreateDepartmentDto;
import com.yourname.equipment.department.dto.DepartmentResponseDto;
import com.yourname.equipment.department.dto.UpdateDepartmentDto;
import com.yourname.equipment.department.entity.Department;
import com.yourname.equipment.department.mapper.DepartmentMapper;
import com.yourname.equipment.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с отделами.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentMapper departmentMapper;

    /**
     * Создать новый отдел.
     */
    public DepartmentResponseDto create(CreateDepartmentDto dto) {
        log.debug("Создание отдела: {}", dto.getName());
        
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));

        Department department = departmentMapper.toEntity(dto);
        department.setCompany(company);

        if (dto.getParentDepartmentId() != null) {
            Department parentDepartment = departmentRepository.findById(dto.getParentDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Отдел", dto.getParentDepartmentId()));
            department.setParentDepartment(parentDepartment);
        }

        if (department.getActive() == null) {
            department.setActive(true);
        }

        Department saved = departmentRepository.save(department);
        log.info("Отдел создан: id={}, name={}", saved.getId(), saved.getName());
        
        return departmentMapper.toDto(saved);
    }

    /**
     * Получить отдел по ID.
     */
    @Transactional(readOnly = true)
    public DepartmentResponseDto getById(Long id) {
        log.debug("Получение отдела: id={}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отдел", id));
        
        return departmentMapper.toDto(department);
    }

    /**
     * Получить все отделы компании с пагинацией.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentResponseDto> getByCompanyId(Long companyId, Pageable pageable) {
        log.debug("Получение отделов компании: companyId={}", companyId);
        
        return departmentRepository.findByCompanyId(companyId, pageable)
                .map(departmentMapper::toDto);
    }

    /**
     * Обновить отдел.
     */
    public DepartmentResponseDto update(Long id, UpdateDepartmentDto dto) {
        log.debug("Обновление отдела: id={}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отдел", id));

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));
            department.setCompany(company);
        }

        if (dto.getParentDepartmentId() != null) {
            Department parentDepartment = departmentRepository.findById(dto.getParentDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Отдел", dto.getParentDepartmentId()));
            department.setParentDepartment(parentDepartment);
        }

        departmentMapper.updateEntity(department, dto);
        Department saved = departmentRepository.save(department);
        log.info("Отдел обновлен: id={}", saved.getId());
        
        return departmentMapper.toDto(saved);
    }

    /**
     * Удалить отдел.
     */
    public void delete(Long id) {
        log.debug("Удаление отдела: id={}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отдел", id));
        
        departmentRepository.delete(department);
        log.info("Отдел удален: id={}", id);
    }
}
