package com.yourname.equipment.equipment.mapper;

import com.yourname.equipment.equipment.dto.*;
import com.yourname.equipment.equipment.entity.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования Equipment Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EquipmentMapper {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "currentUser.id", target = "currentUserId")
    @Mapping(source = "currentUser.fullName", target = "currentUserName")
    EquipmentResponseDto toDto(Equipment entity);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "currentUser", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "repairs", ignore = true)
    @Mapping(target = "upgrades", ignore = true)
    Equipment toEntity(CreateEquipmentDto dto);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "currentUser", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "repairs", ignore = true)
    @Mapping(target = "upgrades", ignore = true)
    void updateEntity(@MappingTarget Equipment entity, UpdateEquipmentDto dto);
}
