package com.yourname.equipment.equipment.dto;

import com.yourname.equipment.equipment.entity.EquipmentRepair;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для ответа с данными ремонта.
 */
@Data
public class EquipmentRepairResponseDto {
    private Long id;
    private Long equipmentId;
    private String equipmentSerialNumber;
    private String description;
    private String repairType;
    private BigDecimal cost;
    private String vendor;
    private EquipmentRepair.RepairStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long reportedByUserId;
    private String reportedByUserName;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
