package com.yourname.equipment.department.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO для ответа с данными отдела.
 */
@Data
public class DepartmentResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private String name;
    private Long parentDepartmentId;
    private String parentDepartmentName;
    private String description;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
