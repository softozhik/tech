package com.yourname.equipment.company.service;

import com.yourname.equipment.common.exception.BusinessException;
import com.yourname.equipment.common.exception.ResourceNotFoundException;
import com.yourname.equipment.company.dto.CompanyResponseDto;
import com.yourname.equipment.company.dto.CreateCompanyDto;
import com.yourname.equipment.company.dto.UpdateCompanyDto;
import com.yourname.equipment.company.entity.Company;
import com.yourname.equipment.company.mapper.CompanyMapper;
import com.yourname.equipment.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с компаниями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    /**
     * Создать новую компанию.
     */
    public CompanyResponseDto create(CreateCompanyDto dto) {
        log.debug("Создание компании: {}", dto.getName());
        
        if (companyRepository.existsByName(dto.getName())) {
            throw new BusinessException("Компания с названием '" + dto.getName() + "' уже существует");
        }

        Company company = companyMapper.toEntity(dto);
        if (company.getActive() == null) {
            company.setActive(true);
        }
        
        Company saved = companyRepository.save(company);
        log.info("Компания создана: id={}, name={}", saved.getId(), saved.getName());
        
        return companyMapper.toDto(saved);
    }

    /**
     * Получить компанию по ID.
     */
    @Transactional(readOnly = true)
    public CompanyResponseDto getById(Long id) {
        log.debug("Получение компании: id={}", id);
        
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Компания", id));
        
        return companyMapper.toDto(company);
    }

    /**
     * Получить все компании с пагинацией.
     */
    @Transactional(readOnly = true)
    public Page<CompanyResponseDto> getAll(Pageable pageable) {
        log.debug("Получение всех компаний: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        
        return companyRepository.findAll(pageable)
                .map(companyMapper::toDto);
    }

    /**
     * Обновить компанию.
     */
    public CompanyResponseDto update(Long id, UpdateCompanyDto dto) {
        log.debug("Обновление компании: id={}", id);
        
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Компания", id));

        if (dto.getName() != null && !dto.getName().equals(company.getName())) {
            if (companyRepository.existsByName(dto.getName())) {
                throw new BusinessException("Компания с названием '" + dto.getName() + "' уже существует");
            }
        }

        companyMapper.updateEntity(company, dto);
        Company saved = companyRepository.save(company);
        log.info("Компания обновлена: id={}", saved.getId());
        
        return companyMapper.toDto(saved);
    }

    /**
     * Удалить компанию.
     */
    public void delete(Long id) {
        log.debug("Удаление компании: id={}", id);
        
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Компания", id));
        
        companyRepository.delete(company);
        log.info("Компания удалена: id={}", id);
    }
}
