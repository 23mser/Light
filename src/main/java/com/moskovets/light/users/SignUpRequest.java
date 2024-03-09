package com.moskovets.light.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Логин пользователь", example = "login123")
    @Size(min = 5, max = 50, message = "Логин пользователя должен содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Номер телефона", example = "79111516141")
    @Size(min = 10, max = 11, message = "Номер телефона должен содержать от 10 до 11 символов")
    @NotBlank(message = "Номер телефона не может быть пустыми")
    private String phone;

    @Schema(description = "Пароль", example = "password")
    @Size(min = 8, max = 50, message = "Длина пароля должна быть от 8 до 50 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
