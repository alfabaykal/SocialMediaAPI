package com.alfabaykal.socialmediaapi.facade;

public interface ChatRequestFacade {

    void sendChatRequest(Long senderId, Long receiverId, Long chatId);

    void acceptChatRequest(Long chatRequestId, Long receiverId);

    void declineChatRequest(Long chatRequestId, Long receiverId);

}
