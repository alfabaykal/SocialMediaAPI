package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.dto.ChatDto;
import com.alfabaykal.socialmediaapi.repository.ChatRepository;
import com.alfabaykal.socialmediaapi.service.ChatService;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final EntityDtoConverter entityDtoConverter;

    public ChatServiceImpl(ChatRepository chatRepository, EntityDtoConverter entityDtoConverter) {
        this.chatRepository = chatRepository;
        this.entityDtoConverter = entityDtoConverter;
    }

    public Optional<ChatDto> getChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .map(entityDtoConverter::convertChatToChatDto);
    }

    @Transactional
    public void save(ChatDto chatDto) {
        chatRepository.save(entityDtoConverter.convertChatDtoToChat(chatDto));
    }

    @Transactional
    public void delete(Long id) {
        chatRepository.deleteById(id);
    }

}
