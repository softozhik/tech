package com.yourname.equipment.equipment.dto;

import com.yourname.equipment.equipment.entity.Equipment;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO для ответа с данными техники.
 */
@Data
public class EquipmentResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private Long categoryId;
    private String categoryName;
    private Long currentUserId;
    private String currentUserName;
    private String serialNumber;
    private String inventoryNumber;
    private String manufacturer;
    private String model;
    private String specifications;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private LocalDate warrantyUntil;
    private Equipment.EquipmentStatus status;
    private Equipment.ConditionStatus conditionStatus;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
