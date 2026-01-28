package com.yourname.equipment.license.service;

import com.yourname.equipment.common.exception.ResourceNotFoundException;
import com.yourname.equipment.company.entity.Company;
import com.yourname.equipment.company.repository.CompanyRepository;
import com.yourname.equipment.license.dto.CreateSoftwareLicenseDto;
import com.yourname.equipment.license.dto.SoftwareLicenseResponseDto;
import com.yourname.equipment.license.dto.UpdateSoftwareLicenseDto;
import com.yourname.equipment.license.entity.SoftwareLicense;
import com.yourname.equipment.license.mapper.SoftwareLicenseMapper;
import com.yourname.equipment.license.repository.SoftwareLicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с лицензиями на ПО.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SoftwareLicenseService {

    private final SoftwareLicenseRepository licenseRepository;
    private final CompanyRepository companyRepository;
    private final SoftwareLicenseMapper licenseMapper;

    /**
     * Создать новую лицензию.
     */
    public SoftwareLicenseResponseDto create(CreateSoftwareLicenseDto dto) {
        log.debug("Создание лицензии: softwareName={}", dto.getSoftwareName());
        
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));

        SoftwareLicense license = licenseMapper.toEntity(dto);
        license.setCompany(company);

        if (license.getSeatsTotal() == null) {
            license.setSeatsTotal(1);
        }
        if (license.getSeatsUsed() == null) {
            license.setSeatsUsed(0);
        }
        if (license.getAutoRenewal() == null) {
            license.setAutoRenewal(false);
        }
        if (license.getStatus() == null) {
            license.setStatus(SoftwareLicense.LicenseStatus.ACTIVE);
        }

        SoftwareLicense saved = licenseRepository.save(license);
        log.info("Лицензия создана: id={}, softwareName={}", saved.getId(), saved.getSoftwareName());
        
        return licenseMapper.toDto(saved);
    }

    /**
     * Получить лицензию по ID.
     */
    @Transactional(readOnly = true)
    public SoftwareLicenseResponseDto getById(Long id) {
        log.debug("Получение лицензии: id={}", id);
        
        SoftwareLicense license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Лицензия", id));
        
        return licenseMapper.toDto(license);
    }

    /**
     * Получить все лицензии компании с пагинацией.
     */
    @Transactional(readOnly = true)
    public Page<SoftwareLicenseResponseDto> getByCompanyId(Long companyId, Pageable pageable) {
        log.debug("Получение лицензий компании: companyId={}", companyId);
        
        return licenseRepository.findByCompanyId(companyId, pageable)
                .map(licenseMapper::toDto);
    }

    /**
     * Обновить лицензию.
     */
    public SoftwareLicenseResponseDto update(Long id, UpdateSoftwareLicenseDto dto) {
        log.debug("Обновление лицензии: id={}", id);
        
        SoftwareLicense license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Лицензия", id));

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));
            license.setCompany(company);
        }

        licenseMapper.updateEntity(license, dto);
        SoftwareLicense saved = licenseRepository.save(license);
        log.info("Лицензия обновлена: id={}", saved.getId());
        
        return licenseMapper.toDto(saved);
    }

    /**
     * Удалить лицензию.
     */
    public void delete(Long id) {
        log.debug("Удаление лицензии: id={}", id);
        
        SoftwareLicense license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Лицензия", id));
        
        licenseRepository.delete(license);
        log.info("Лицензия удалена: id={}", id);
    }

    /**
     * Получить истекающие лицензии.
     */
    @Transactional(readOnly = true)
    public List<SoftwareLicenseResponseDto> getExpiringLicenses(Long companyId, int days) {
        log.debug("Получение истекающих лицензий: companyId={}, days={}", companyId, days);
        
        java.time.LocalDate expiryDate = java.time.LocalDate.now().plusDays(days);
        List<SoftwareLicense> licenses = licenseRepository.findExpiringLicenses(companyId, expiryDate);
        
        return licenses.stream()
                .map(licenseMapper::toDto)
                .collect(Collectors.toList());
    }
}
