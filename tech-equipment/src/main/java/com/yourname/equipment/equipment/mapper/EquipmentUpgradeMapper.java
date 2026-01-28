package com.yourname.equipment.equipment.mapper;

import com.yourname.equipment.equipment.dto.CreateUpgradeDto;
import com.yourname.equipment.equipment.dto.EquipmentUpgradeResponseDto;
import com.yourname.equipment.equipment.entity.EquipmentUpgrade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования EquipmentUpgrade Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EquipmentUpgradeMapper {

    @Mapping(source = "equipment.id", target = "equipmentId")
    @Mapping(source = "equipment.serialNumber", target = "equipmentSerialNumber")
    @Mapping(source = "performedByUser.id", target = "performedByUserId")
    @Mapping(source = "performedByUser.fullName", target = "performedByUserName")
    EquipmentUpgradeResponseDto toDto(EquipmentUpgrade entity);

    @Mapping(target = "equipment", ignore = true)
    @Mapping(target = "performedByUser", ignore = true)
    EquipmentUpgrade toEntity(CreateUpgradeDto dto);
}
