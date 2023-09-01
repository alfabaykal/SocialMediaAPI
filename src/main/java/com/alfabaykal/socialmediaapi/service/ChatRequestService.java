package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.model.ChatRequest;

import java.util.Optional;

public interface ChatRequestService {

    void sendChatRequest(Long senderId, Long receiverId, Long chatId);

    void acceptChatRequest(Long chatRequestId, Long receiverId);

    void declineChatRequest(Long chatRequestId, Long receiverId);

    Optional<ChatRequest> getChatRequestBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
