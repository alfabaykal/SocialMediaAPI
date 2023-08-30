package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.dto.ChatDto;

import java.util.Optional;

public interface ChatService {

    Optional<ChatDto> getChatById(Long chatId);

    void save(ChatDto chatDto);

    void delete(Long id);
}
