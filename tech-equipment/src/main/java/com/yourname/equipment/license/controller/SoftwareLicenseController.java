package com.yourname.equipment.license.controller;

import com.yourname.equipment.license.dto.CreateSoftwareLicenseDto;
import com.yourname.equipment.license.dto.SoftwareLicenseResponseDto;
import com.yourname.equipment.license.dto.UpdateSoftwareLicenseDto;
import com.yourname.equipment.license.service.SoftwareLicenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для работы с лицензиями на ПО.
 */
@RestController
@RequestMapping("/api/licenses")
@RequiredArgsConstructor
@Tag(name = "Software Licenses", description = "API для управления лицензиями на ПО")
public class SoftwareLicenseController {

    private final SoftwareLicenseService licenseService;

    @PostMapping
    @Operation(summary = "Создать лицензию")
    public ResponseEntity<SoftwareLicenseResponseDto> create(@Valid @RequestBody CreateSoftwareLicenseDto dto) {
        SoftwareLicenseResponseDto response = licenseService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить лицензию по ID")
    public ResponseEntity<SoftwareLicenseResponseDto> getById(@PathVariable Long id) {
        SoftwareLicenseResponseDto response = licenseService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Получить все лицензии компании")
    public ResponseEntity<Page<SoftwareLicenseResponseDto>> getByCompanyId(@PathVariable Long companyId, 
                                                                           Pageable pageable) {
        Page<SoftwareLicenseResponseDto> response = licenseService.getByCompanyId(companyId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить лицензию")
    public ResponseEntity<SoftwareLicenseResponseDto> update(@PathVariable Long id, 
                                                             @Valid @RequestBody UpdateSoftwareLicenseDto dto) {
        SoftwareLicenseResponseDto response = licenseService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить лицензию")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        licenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}/expiring")
    @Operation(summary = "Получить истекающие лицензии")
    public ResponseEntity<List<SoftwareLicenseResponseDto>> getExpiringLicenses(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "30") int days) {
        List<SoftwareLicenseResponseDto> response = licenseService.getExpiringLicenses(companyId, days);
        return ResponseEntity.ok(response);
    }
}
