package com.yourname.equipment.equipment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для ответа с данными апгрейда.
 */
@Data
public class EquipmentUpgradeResponseDto {
    private Long id;
    private Long equipmentId;
    private String equipmentSerialNumber;
    private String description;
    private String upgradeType;
    private String componentName;
    private String oldValue;
    private String newValue;
    private BigDecimal cost;
    private LocalDateTime upgradedAt;
    private Long performedByUserId;
    private String performedByUserName;
    private String notes;
    private LocalDateTime createdAt;
}
