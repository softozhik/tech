package com.yourname.equipment.user.dto;

import com.yourname.equipment.user.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO для ответа с данными пользователя.
 */
@Data
public class UserResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private Long departmentId;
    private String departmentName;
    private String email;
    private String fullName;
    private User.UserRole role;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
