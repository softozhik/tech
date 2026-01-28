package com.yourname.equipment.equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO для ответа с данными назначения техники.
 */
@Data
public class EquipmentAssignmentResponseDto {
    private Long id;
    private Long equipmentId;
    private String equipmentSerialNumber;
    private Long userId;
    private String userName;
    private Long departmentId;
    private String departmentName;
    private LocalDateTime assignedAt;
    private LocalDateTime returnedAt;
    private Long assignedByUserId;
    private String assignedByUserName;
    private String notes;
    private LocalDateTime createdAt;
}
