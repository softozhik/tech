package com.yourname.equipment.equipment.mapper;

import com.yourname.equipment.equipment.dto.CreateRepairDto;
import com.yourname.equipment.equipment.dto.EquipmentRepairResponseDto;
import com.yourname.equipment.equipment.entity.EquipmentRepair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования EquipmentRepair Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EquipmentRepairMapper {

    @Mapping(source = "equipment.id", target = "equipmentId")
    @Mapping(source = "equipment.serialNumber", target = "equipmentSerialNumber")
    @Mapping(source = "reportedByUser.id", target = "reportedByUserId")
    @Mapping(source = "reportedByUser.fullName", target = "reportedByUserName")
    EquipmentRepairResponseDto toDto(EquipmentRepair entity);

    @Mapping(target = "equipment", ignore = true)
    @Mapping(target = "reportedByUser", ignore = true)
    EquipmentRepair toEntity(CreateRepairDto dto);
}
