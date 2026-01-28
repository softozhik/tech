package com.yourname.equipment.department.mapper;

import com.yourname.equipment.department.dto.CreateDepartmentDto;
import com.yourname.equipment.department.dto.DepartmentResponseDto;
import com.yourname.equipment.department.dto.UpdateDepartmentDto;
import com.yourname.equipment.department.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования Department Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "parentDepartment.id", target = "parentDepartmentId")
    @Mapping(source = "parentDepartment.name", target = "parentDepartmentName")
    DepartmentResponseDto toDto(Department entity);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "parentDepartment", ignore = true)
    Department toEntity(CreateDepartmentDto dto);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "parentDepartment", ignore = true)
    void updateEntity(@MappingTarget Department entity, UpdateDepartmentDto dto);
}
