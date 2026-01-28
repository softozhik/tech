package com.yourname.equipment.company.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для обновления компании.
 */
@Data
public class UpdateCompanyDto {

    @Size(max = 255, message = "Название не должно превышать 255 символов")
    private String name;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    private Boolean active;
}
