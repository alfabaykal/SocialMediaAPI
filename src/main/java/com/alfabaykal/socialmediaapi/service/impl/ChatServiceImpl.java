package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.model.Chat;
import com.alfabaykal.socialmediaapi.repository.ChatRepository;
import com.alfabaykal.socialmediaapi.service.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Optional<Chat> getChatById(Long chatId) {
        return chatRepository.findById(chatId);
    }

    @Transactional
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Transactional
    public void delete(Long id) {
        chatRepository.deleteById(id);
    }

}
