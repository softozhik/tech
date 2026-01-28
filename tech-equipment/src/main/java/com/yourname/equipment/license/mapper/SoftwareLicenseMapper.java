package com.yourname.equipment.license.mapper;

import com.yourname.equipment.license.dto.CreateSoftwareLicenseDto;
import com.yourname.equipment.license.dto.SoftwareLicenseResponseDto;
import com.yourname.equipment.license.dto.UpdateSoftwareLicenseDto;
import com.yourname.equipment.license.entity.SoftwareLicense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования SoftwareLicense Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SoftwareLicenseMapper {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    SoftwareLicenseResponseDto toDto(SoftwareLicense entity);

    @Mapping(target = "company", ignore = true)
    SoftwareLicense toEntity(CreateSoftwareLicenseDto dto);

    @Mapping(target = "company", ignore = true)
    void updateEntity(@MappingTarget SoftwareLicense entity, UpdateSoftwareLicenseDto dto);
}
