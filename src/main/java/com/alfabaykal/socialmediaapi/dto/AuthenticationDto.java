package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Информация для аутентификации пользователя")
@Getter
@Setter
public class AuthenticationDto {

    @Schema(description = "Имя пользователя")
    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 2, max = 100, message = "Username should be from 2 to 100 characters long ")
    private String username;

    @Schema(description = "Пароль")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;
}
