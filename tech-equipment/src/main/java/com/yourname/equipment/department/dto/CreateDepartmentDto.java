package com.yourname.equipment.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для создания отдела.
 */
@Data
public class CreateDepartmentDto {

    @NotNull(message = "ID компании обязателен")
    private Long companyId;

    @NotBlank(message = "Название отдела обязательно")
    @Size(max = 255, message = "Название не должно превышать 255 символов")
    private String name;

    private Long parentDepartmentId;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    private Boolean active;
}
