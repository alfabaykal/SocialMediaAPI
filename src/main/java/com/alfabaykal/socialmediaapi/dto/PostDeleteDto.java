package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Информация необходимая для удаления поста")
@Getter
@Setter
public class PostDeleteDto {
    @Schema(description = "Идентификатор поста")
    private Long postId;
}
