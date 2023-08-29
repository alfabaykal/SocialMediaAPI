package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.dto.ChatDto;
import com.alfabaykal.socialmediaapi.dto.ChatRequestDto;
import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.*;
import com.alfabaykal.socialmediaapi.repository.ChatRequestRepository;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ChatRequestService {

    private final ChatRequestRepository chatRequestRepository;
    private final ChatService chatService;
    private final UserService userService;
    private final EntityDtoConverter entityDtoConverter;

    public ChatRequestService(ChatRequestRepository chatRequestRepository,
                              ChatService chatService,
                              UserService userService, EntityDtoConverter entityDtoConverter) {
        this.chatRequestRepository = chatRequestRepository;
        this.chatService = chatService;
        this.userService = userService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void sendChatRequest(Long senderId, Long receiverId, Long chatId) {
        UserRelationshipDto sender = userService.getUserByIdWithFriends(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userService.getUserByIdWithFriends(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        if (alreadySent(sender, receiver, chatId))
            throw new MultipleChatRequestException();

        if (sender.getFriends().contains(receiver))
            chatRequestRepository
                    .save(entityDtoConverter
                            .convertChatRequestDtoToChatRequest
                                    (new ChatRequestDto(sender, receiver,
                                            chatId == null ? null : chatService.getChatById(chatId)
                                                    .orElseThrow(() -> new ChatNotFoundException(chatId)))));
        else
            throw new NotFriendException();
    }

    @Transactional
    public void acceptChatRequest(Long chatRequestId, Long receiverId) {
        ChatRequestDto chatRequestDto = chatRequestRepository.findById(chatRequestId)
                .map(entityDtoConverter::convertChatRequestToChatRequestDto)
                .orElseThrow(() -> new ChatRequestNotFoundException(chatRequestId));
        UserDto sender = userService.getUserById(chatRequestDto.getSender().getId())
                .orElseThrow(() -> new UserNotFoundException(chatRequestDto.getSender().getId()));
        UserDto receiver = userService.getUserById(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        Long chatId = chatRequestDto.getChatDto() == null ? null :
                chatRequestDto.getChatDto().getId();

        ChatDto chatDto = chatId == null ? new ChatDto() :
                chatService.getChatById(chatId)
                        .orElseThrow(() -> new ChatNotFoundException(chatId));

        if (chatId == null)
            chatDto.setUsers(Set.of(sender));

        Set<UserDto> chatUsers = new HashSet<>(chatDto.getUsers());

        if (!chatDto.getUsers().contains(receiver))
            chatUsers.add(receiver);

        chatDto.setUsers(chatUsers);

        chatRequestRepository.deleteById(chatRequestId);
        chatService.save(chatDto);
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

    private boolean alreadySent(UserRelationshipDto sender, UserRelationshipDto receiver, Long chatId) {
        return chatRequestRepository
                .existsBySenderAndReceiver(
                        entityDtoConverter.convertUserRelationshipDtoToUser(sender),
                        entityDtoConverter.convertUserRelationshipDtoToUser(receiver));
    }

}
