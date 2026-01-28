package com.yourname.equipment.company.controller;

import com.yourname.equipment.company.dto.CompanyResponseDto;
import com.yourname.equipment.company.dto.CreateCompanyDto;
import com.yourname.equipment.company.dto.UpdateCompanyDto;
import com.yourname.equipment.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для работы с компаниями.
 */
@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Companies", description = "API для управления компаниями")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @Operation(summary = "Создать компанию")
    public ResponseEntity<CompanyResponseDto> create(@Valid @RequestBody CreateCompanyDto dto) {
        CompanyResponseDto response = companyService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить компанию по ID")
    public ResponseEntity<CompanyResponseDto> getById(@PathVariable Long id) {
        CompanyResponseDto response = companyService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Получить все компании")
    public ResponseEntity<Page<CompanyResponseDto>> getAll(Pageable pageable) {
        Page<CompanyResponseDto> response = companyService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить компанию")
    public ResponseEntity<CompanyResponseDto> update(@PathVariable Long id, 
                                                      @Valid @RequestBody UpdateCompanyDto dto) {
        CompanyResponseDto response = companyService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить компанию")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
