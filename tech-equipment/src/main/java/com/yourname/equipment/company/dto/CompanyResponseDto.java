package com.yourname.equipment.company.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO для ответа с данными компании.
 */
@Data
public class CompanyResponseDto {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
