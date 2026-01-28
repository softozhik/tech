package com.yourname.equipment.user.mapper;

import com.yourname.equipment.user.dto.CreateUserDto;
import com.yourname.equipment.user.dto.UpdateUserDto;
import com.yourname.equipment.user.dto.UserResponseDto;
import com.yourname.equipment.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер для преобразования User Entity ↔ DTO.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    UserResponseDto toDto(User entity);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    User toEntity(CreateUserDto dto);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    void updateEntity(@MappingTarget User entity, UpdateUserDto dto);
}
