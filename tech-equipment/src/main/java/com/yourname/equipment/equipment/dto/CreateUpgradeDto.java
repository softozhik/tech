package com.yourname.equipment.equipment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для создания записи об апгрейде.
 */
@Data
public class CreateUpgradeDto {

    @NotBlank(message = "Описание обязательно")
    private String description;

    @Size(max = 50, message = "Тип апгрейда не должен превышать 50 символов")
    private String upgradeType;

    @Size(max = 255, message = "Название компонента не должно превышать 255 символов")
    private String componentName;

    @Size(max = 255, message = "Старое значение не должно превышать 255 символов")
    private String oldValue;

    @Size(max = 255, message = "Новое значение не должно превышать 255 символов")
    private String newValue;

    private BigDecimal cost;

    @NotNull(message = "Дата апгрейда обязательна")
    private LocalDateTime upgradedAt;

    private String notes;
}
