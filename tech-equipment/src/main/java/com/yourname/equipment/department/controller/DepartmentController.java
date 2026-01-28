package com.yourname.equipment.department.controller;

import com.yourname.equipment.department.dto.CreateDepartmentDto;
import com.yourname.equipment.department.dto.DepartmentResponseDto;
import com.yourname.equipment.department.dto.UpdateDepartmentDto;
import com.yourname.equipment.department.service.DepartmentService;
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
 * REST контроллер для работы с отделами.
 */
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Departments", description = "API для управления отделами")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @Operation(summary = "Создать отдел")
    public ResponseEntity<DepartmentResponseDto> create(@Valid @RequestBody CreateDepartmentDto dto) {
        DepartmentResponseDto response = departmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отдел по ID")
    public ResponseEntity<DepartmentResponseDto> getById(@PathVariable Long id) {
        DepartmentResponseDto response = departmentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Получить все отделы компании")
    public ResponseEntity<Page<DepartmentResponseDto>> getByCompanyId(@PathVariable Long companyId, 
                                                                     Pageable pageable) {
        Page<DepartmentResponseDto> response = departmentService.getByCompanyId(companyId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить отдел")
    public ResponseEntity<DepartmentResponseDto> update(@PathVariable Long id, 
                                                       @Valid @RequestBody UpdateDepartmentDto dto) {
        DepartmentResponseDto response = departmentService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отдел")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
