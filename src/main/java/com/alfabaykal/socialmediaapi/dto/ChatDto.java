package com.alfabaykal.socialmediaapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ChatDto {
    private Long id;
    private Set<UserDto> users;
    private Set<MessageDto> messages;
    private LocalDateTime createdAt;
}
