package com.yourname.equipment.report.service;

import com.yourname.equipment.common.exception.ResourceNotFoundException;
import com.yourname.equipment.equipment.entity.Equipment;
import com.yourname.equipment.equipment.entity.EquipmentRepair;
import com.yourname.equipment.equipment.entity.EquipmentUpgrade;
import com.yourname.equipment.equipment.repository.EquipmentRepository;
import com.yourname.equipment.license.entity.SoftwareLicense;
import com.yourname.equipment.license.repository.SoftwareLicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервис для генерации отчетов.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReportService {

    private final EquipmentRepository equipmentRepository;
    private final SoftwareLicenseRepository licenseRepository;

    /**
     * Получить отчет по технике по отделам.
     */
    public Map<String, Long> getEquipmentByDepartment(Long companyId) {
        log.debug("Генерация отчета по технике по отделам: companyId={}", companyId);
        
        List<Equipment> equipmentList = equipmentRepository.findByCompanyId(companyId, 
                org.springframework.data.domain.Pageable.unpaged()).getContent();
        
        Map<String, Long> result = new HashMap<>();
        for (Equipment equipment : equipmentList) {
            if (equipment.getCurrentUser() != null && equipment.getCurrentUser().getDepartment() != null) {
                String departmentName = equipment.getCurrentUser().getDepartment().getName();
                result.put(departmentName, result.getOrDefault(departmentName, 0L) + 1);
            } else {
                result.put("Не назначено", result.getOrDefault("Не назначено", 0L) + 1);
            }
        }
        
        return result;
    }

    /**
     * Получить общую стоимость техники (покупка + ремонты + апгрейды).
     */
    public Map<String, BigDecimal> getEquipmentTotalCost(Long equipmentId) {
        log.debug("Расчет общей стоимости техники: equipmentId={}", equipmentId);
        
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", equipmentId));
        
        BigDecimal purchaseCost = equipment.getPurchasePrice() != null ? equipment.getPurchasePrice() : BigDecimal.ZERO;
        
        BigDecimal repairCost = equipment.getRepairs().stream()
                .map(EquipmentRepair::getCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal upgradeCost = equipment.getUpgrades().stream()
                .map(EquipmentUpgrade::getCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalCost = purchaseCost.add(repairCost).add(upgradeCost);
        
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("purchaseCost", purchaseCost);
        result.put("repairCost", repairCost);
        result.put("upgradeCost", upgradeCost);
        result.put("totalCost", totalCost);
        
        return result;
    }

    /**
     * Получить истекающие лицензии.
     */
    public List<Map<String, Object>> getLicensesExpiring(Long companyId, int days) {
        log.debug("Получение истекающих лицензий: companyId={}, days={}", companyId, days);
        
        LocalDate expiryDate = LocalDate.now().plusDays(days);
        List<SoftwareLicense> licenses = licenseRepository.findExpiringLicenses(companyId, expiryDate);
        
        return licenses.stream()
                .map(license -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", license.getId());
                    map.put("softwareName", license.getSoftwareName());
                    map.put("expiryDate", license.getExpiryDate());
                    map.put("status", license.getStatus());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
