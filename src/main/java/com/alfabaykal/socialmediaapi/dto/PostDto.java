package com.alfabaykal.socialmediaapi.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Информация для создания нового поста")
@Getter
@Setter
public class PostDto {

    @Hidden
    private Long id;

    @Hidden
    private UserDto author;

    @Schema(description = "Заголовок поста")
    @NotEmpty(message = "Title shouldn't be empty")
    private String title;

    @Schema(description = "Текст поста")
    private String text;

    @Schema(description = "Ссылка на прикрепленное изображение")
    private String imageUrl;

    @Hidden
    private LocalDateTime createdAt;
}
