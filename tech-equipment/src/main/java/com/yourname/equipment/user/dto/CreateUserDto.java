package com.yourname.equipment.user.dto;

import com.yourname.equipment.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для создания пользователя.
 */
@Data
public class CreateUserDto {

    @NotNull(message = "ID компании обязателен")
    private Long companyId;

    private Long departmentId;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Size(max = 255, message = "Email не должен превышать 255 символов")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @NotBlank(message = "Полное имя обязательно")
    @Size(max = 255, message = "Полное имя не должно превышать 255 символов")
    private String fullName;

    @NotNull(message = "Роль обязательна")
    private User.UserRole role;

    private User.UserStatus status;
}
