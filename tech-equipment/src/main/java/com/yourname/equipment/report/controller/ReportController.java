package com.yourname.equipment.report.controller;

import com.yourname.equipment.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * REST контроллер для генерации отчетов.
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "API для генерации отчетов")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/equipment-by-department")
    @Operation(summary = "Получить отчет по технике по отделам")
    public ResponseEntity<Map<String, Long>> getEquipmentByDepartment(@RequestParam Long companyId) {
        Map<String, Long> response = reportService.getEquipmentByDepartment(companyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipment/{id}/total-cost")
    @Operation(summary = "Получить общую стоимость техники")
    public ResponseEntity<Map<String, BigDecimal>> getEquipmentTotalCost(@PathVariable Long id) {
        Map<String, BigDecimal> response = reportService.getEquipmentTotalCost(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/licenses-expiring")
    @Operation(summary = "Получить истекающие лицензии")
    public ResponseEntity<List<Map<String, Object>>> getLicensesExpiring(
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "30") int days) {
        List<Map<String, Object>> response = reportService.getLicensesExpiring(companyId, days);
        return ResponseEntity.ok(response);
    }
}
