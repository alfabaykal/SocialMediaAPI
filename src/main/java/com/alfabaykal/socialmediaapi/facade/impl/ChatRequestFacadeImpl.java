package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.facade.ChatRequestFacade;
import com.alfabaykal.socialmediaapi.service.ChatRequestService;
import org.springframework.stereotype.Service;

@Service
public class ChatRequestFacadeImpl implements ChatRequestFacade {

    private final ChatRequestService chatRequestService;

    public ChatRequestFacadeImpl(ChatRequestService chatRequestService) {
        this.chatRequestService = chatRequestService;
    }

    @Override
    public void sendChatRequest(Long senderId, Long receiverId, Long chatId) {
        chatRequestService.sendChatRequest(senderId, receiverId, chatId);
    }

    @Override
    public void acceptChatRequest(Long chatRequestId, Long receiverId) {
        chatRequestService.acceptChatRequest(chatRequestId, receiverId);
    }

    @Override
    public void declineChatRequest(Long chatRequestId, Long receiverId) {
        chatRequestService.declineChatRequest(chatRequestId, receiverId);
    }
}
