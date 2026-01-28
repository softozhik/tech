package com.yourname.equipment.equipment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO для назначения техники пользователю.
 */
@Data
public class AssignEquipmentDto {

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    private Long departmentId;

    private String notes;
}
