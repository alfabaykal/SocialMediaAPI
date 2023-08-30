package com.alfabaykal.socialmediaapi.service;

public interface ChatRequestService {

    void sendChatRequest(Long senderId, Long receiverId, Long chatId);

    void acceptChatRequest(Long chatRequestId, Long receiverId);

    void declineChatRequest(Long chatRequestId, Long receiverId);

}
