package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Информация для отправки запроса на переписку или приглашения в чат")
@Getter
@Setter
public class ChatRequestInitiationDto {
    @Schema(description = "Идентификатор получателя")
    private Long receiverId;
}
