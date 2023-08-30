package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.model.Chat;

import java.util.Optional;

public interface ChatService {

    Optional<Chat> getChatById(Long chatId);

    void save(Chat chat);

    void delete(Long id);
}
