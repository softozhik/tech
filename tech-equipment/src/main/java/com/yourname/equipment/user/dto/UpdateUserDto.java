package com.yourname.equipment.user.dto;

import com.yourname.equipment.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для обновления пользователя.
 */
@Data
public class UpdateUserDto {

    private Long companyId;

    private Long departmentId;

    @Email(message = "Некорректный формат email")
    @Size(max = 255, message = "Email не должен превышать 255 символов")
    private String email;

    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @Size(max = 255, message = "Полное имя не должно превышать 255 символов")
    private String fullName;

    private User.UserRole role;

    private User.UserStatus status;
}
