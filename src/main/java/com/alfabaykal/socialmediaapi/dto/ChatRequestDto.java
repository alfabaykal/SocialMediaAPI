package com.alfabaykal.socialmediaapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRequestDto {
    private Long id;
    private ChatDto chatDto;
    private UserRelationshipDto sender;
    private UserRelationshipDto receiver;

}
