package com.yourname.equipment.equipment.controller;

import com.yourname.equipment.equipment.dto.*;
import com.yourname.equipment.equipment.entity.Equipment;
import com.yourname.equipment.equipment.service.EquipmentService;
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
 * REST контроллер для работы с техникой.
 */
@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
@Tag(name = "Equipment", description = "API для управления техникой")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    @Operation(summary = "Создать единицу техники")
    public ResponseEntity<EquipmentResponseDto> create(@Valid @RequestBody CreateEquipmentDto dto) {
        EquipmentResponseDto response = equipmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить технику по ID")
    public ResponseEntity<EquipmentResponseDto> getById(@PathVariable Long id) {
        EquipmentResponseDto response = equipmentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Получить всю технику с фильтрами")
    public ResponseEntity<Page<EquipmentResponseDto>> getAll(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Equipment.EquipmentStatus status,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        Page<EquipmentResponseDto> response = equipmentService.getAll(companyId, status, categoryId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить технику")
    public ResponseEntity<EquipmentResponseDto> update(@PathVariable Long id, 
                                                       @Valid @RequestBody UpdateEquipmentDto dto) {
        EquipmentResponseDto response = equipmentService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить технику")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/assign")
    @Operation(summary = "Назначить технику пользователю")
    public ResponseEntity<EquipmentAssignmentResponseDto> assignEquipment(
            @PathVariable Long id, 
            @Valid @RequestBody AssignEquipmentDto dto) {
        EquipmentAssignmentResponseDto response = equipmentService.assignEquipment(id, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Вернуть технику")
    public ResponseEntity<Void> returnEquipment(@PathVariable Long id) {
        equipmentService.returnEquipment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/assignments")
    @Operation(summary = "Получить историю назначений техники")
    public ResponseEntity<List<EquipmentAssignmentResponseDto>> getAssignmentHistory(@PathVariable Long id) {
        List<EquipmentAssignmentResponseDto> response = equipmentService.getAssignmentHistory(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/repairs")
    @Operation(summary = "Получить историю ремонтов техники")
    public ResponseEntity<List<EquipmentRepairResponseDto>> getRepairHistory(@PathVariable Long id) {
        List<EquipmentRepairResponseDto> response = equipmentService.getRepairHistory(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/repairs")
    @Operation(summary = "Создать запись о ремонте")
    public ResponseEntity<EquipmentRepairResponseDto> createRepair(
            @PathVariable Long id, 
            @Valid @RequestBody CreateRepairDto dto) {
        EquipmentRepairResponseDto response = equipmentService.createRepair(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/upgrades")
    @Operation(summary = "Получить историю апгрейдов техники")
    public ResponseEntity<List<EquipmentUpgradeResponseDto>> getUpgradeHistory(@PathVariable Long id) {
        List<EquipmentUpgradeResponseDto> response = equipmentService.getUpgradeHistory(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/upgrades")
    @Operation(summary = "Создать запись об апгрейде")
    public ResponseEntity<EquipmentUpgradeResponseDto> createUpgrade(
            @PathVariable Long id, 
            @Valid @RequestBody CreateUpgradeDto dto) {
        EquipmentUpgradeResponseDto response = equipmentService.createUpgrade(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
