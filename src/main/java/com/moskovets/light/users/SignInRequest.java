package com.moskovets.light.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Логин пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Логин пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Логин пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "password")
    @Size(min = 8, max = 50, message = "Длина пароля должна быть от 8 до 50 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
