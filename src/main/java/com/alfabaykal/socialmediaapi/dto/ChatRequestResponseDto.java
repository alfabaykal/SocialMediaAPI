package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Информация для принятия запроса на переписку или приглашения в чат")
@Getter
@Setter
public class ChatRequestResponseDto {
    @Schema(description = "Идентификатор запроса")
    private Long chatRequestId;
}
