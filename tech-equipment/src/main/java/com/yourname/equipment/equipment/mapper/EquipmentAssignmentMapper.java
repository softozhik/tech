package com.yourname.equipment.equipment.mapper;

import com.yourname.equipment.equipment.dto.EquipmentAssignmentResponseDto;
import com.yourname.equipment.equipment.entity.EquipmentAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования EquipmentAssignment Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EquipmentAssignmentMapper {

    @Mapping(source = "equipment.id", target = "equipmentId")
    @Mapping(source = "equipment.serialNumber", target = "equipmentSerialNumber")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "assignedByUser.id", target = "assignedByUserId")
    @Mapping(source = "assignedByUser.fullName", target = "assignedByUserName")
    EquipmentAssignmentResponseDto toDto(EquipmentAssignment entity);
}
