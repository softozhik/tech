package com.yourname.equipment.company.mapper;

import com.yourname.equipment.company.dto.CompanyResponseDto;
import com.yourname.equipment.company.dto.CreateCompanyDto;
import com.yourname.equipment.company.dto.UpdateCompanyDto;
import com.yourname.equipment.company.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования Company Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    CompanyResponseDto toDto(Company entity);

    Company toEntity(CreateCompanyDto dto);

    void updateEntity(@MappingTarget Company entity, UpdateCompanyDto dto);
}
