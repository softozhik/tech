package com.yourname.equipment.equipment.dto;

import com.yourname.equipment.equipment.entity.Equipment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для создания техники.
 */
@Data
public class CreateEquipmentDto {

    @NotNull(message = "ID компании обязателен")
    private Long companyId;

    @NotNull(message = "ID категории обязателен")
    private Long categoryId;

    private Long currentUserId;

    @NotBlank(message = "Серийный номер обязателен")
    @Size(max = 255, message = "Серийный номер не должен превышать 255 символов")
    private String serialNumber;

    @Size(max = 255, message = "Инвентарный номер не должен превышать 255 символов")
    private String inventoryNumber;

    @Size(max = 255, message = "Производитель не должен превышать 255 символов")
    private String manufacturer;

    @NotBlank(message = "Модель обязательна")
    @Size(max = 255, message = "Модель не должна превышать 255 символов")
    private String model;

    private String specifications;

    private LocalDate purchaseDate;

    private BigDecimal purchasePrice;

    private LocalDate warrantyUntil;

    private Equipment.EquipmentStatus status;

    private Equipment.ConditionStatus conditionStatus;

    private String notes;
}
