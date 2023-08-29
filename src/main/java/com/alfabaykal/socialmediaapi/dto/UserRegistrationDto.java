package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Schema(description = "Информация для регистрации нового пользователя")
@Getter
@Setter
public class UserRegistrationDto {

    @Hidden
    private Long id;

    @Schema(description = "Имя пользователя")
    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 2, max = 100, message = "Username should be from 2 to 100 characters long ")
    private String username;

    @Schema(description = "Электронная почта пользователя")
    @Email
    @NotEmpty(message = "Email shouldn't be empty")
    private String email;

    @Schema(description = "Пароль")
    @Size(min = 6, message = "Password must be 6 characters or more")
    @NotEmpty
    private String password;

    @Hidden
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return id.equals(that.id) && username.equals(that.username) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
}
