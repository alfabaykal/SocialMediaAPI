package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.exception.*;
import com.alfabaykal.socialmediaapi.model.Chat;
import com.alfabaykal.socialmediaapi.model.ChatRequest;
import com.alfabaykal.socialmediaapi.model.User;
import com.alfabaykal.socialmediaapi.repository.ChatRequestRepository;
import com.alfabaykal.socialmediaapi.service.ChatRequestService;
import com.alfabaykal.socialmediaapi.service.ChatService;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class ChatRequestServiceImpl implements ChatRequestService {

    private final ChatRequestRepository chatRequestRepository;
    private final ChatService chatService;
    private final UserService userService;

    public ChatRequestServiceImpl(ChatRequestRepository chatRequestRepository,
                                  ChatService chatService,
                                  UserService userService) {
        this.chatRequestRepository = chatRequestRepository;
        this.chatService = chatService;
        this.userService = userService;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void sendChatRequest(Long senderId, Long receiverId, Long chatId) {
        User sender = userService.getUserByIdWithFriends(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = userService.getUserByIdWithFriends(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        if (alreadySent(sender, receiver))
            throw new MultipleChatRequestException();

        if (sender.getFriends().contains(receiver))
            chatRequestRepository
                    .save(new ChatRequest(sender, receiver,
                                            chatId == null ? null : chatService.getChatById(chatId)
                                                    .orElseThrow(() -> new ChatNotFoundException(chatId))));
        else
            throw new NotFriendException();
    }

    @Transactional
    public void acceptChatRequest(Long chatRequestId, Long receiverId) {
        ChatRequest chatRequest = chatRequestRepository.findById(chatRequestId)
                .orElseThrow(() -> new ChatRequestNotFoundException(chatRequestId));
        User sender = userService.getUserById(chatRequest.getSender().getId())
                .orElseThrow(() -> new UserNotFoundException(chatRequest.getSender().getId()));
        User receiver = userService.getUserById(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        Long chatId = chatRequest.getChat() == null ? null :
                chatRequest.getChat().getId();

        Chat chat = chatId == null ? new Chat() :
                chatService.getChatById(chatId)
                        .orElseThrow(() -> new ChatNotFoundException(chatId));

        if (chatId == null)
            chat.setUsers(Set.of(sender));

        Set<User> chatUsers = new HashSet<>(chat.getUsers());

        if (!chat.getUsers().contains(receiver))
            chatUsers.add(receiver);

        chat.setUsers(chatUsers);

        chatRequestRepository.deleteById(chatRequestId);
        chatService.save(chat);
    }

    @Transactional
    public void declineChatRequest(Long chatRequestId, Long receiverId) {
        if (chatRequestRepository.findById(chatRequestId)
                .orElseThrow(() -> new ChatRequestNotFoundException(chatRequestId))
                .getReceiver().getId().equals(receiverId)) {
            chatRequestRepository.deleteById(chatRequestId);
        } else
            throw new NotYourChatRequestException();
    }

    @Override
    public Optional<ChatRequest> getChatRequestBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        return chatRequestRepository.findBySenderAndReceiver(userService.getUserById(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId)),
                userService.getUserById(receiverId)
                        .orElseThrow(() -> new UserNotFoundException(receiverId)));
    }

    private boolean alreadySent(User sender, User receiver) {
        return chatRequestRepository.existsBySenderAndReceiver(sender, receiver);
    }

}
