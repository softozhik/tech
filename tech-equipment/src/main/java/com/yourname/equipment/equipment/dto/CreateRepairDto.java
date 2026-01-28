package com.yourname.equipment.equipment.dto;

import com.yourname.equipment.equipment.entity.EquipmentRepair;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для создания записи о ремонте.
 */
@Data
public class CreateRepairDto {

    @NotBlank(message = "Описание обязательно")
    private String description;

    @Size(max = 50, message = "Тип ремонта не должен превышать 50 символов")
    private String repairType;

    private BigDecimal cost;

    @Size(max = 255, message = "Поставщик не должен превышать 255 символов")
    private String vendor;

    private EquipmentRepair.RepairStatus status;

    @NotNull(message = "Дата начала обязательна")
    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private String notes;
}
