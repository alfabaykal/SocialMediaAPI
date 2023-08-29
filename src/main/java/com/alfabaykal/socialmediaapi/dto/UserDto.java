package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Информация получаемая при создании/редактировании/получении пользователей")
@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

    @Schema(description = "Идентификатор пользователя")
    private Long id;

    @Schema(description = "Имя пользователя")
    private String username;

    @Schema(description = "Электронная почта пользователя")
    private String email;
}
