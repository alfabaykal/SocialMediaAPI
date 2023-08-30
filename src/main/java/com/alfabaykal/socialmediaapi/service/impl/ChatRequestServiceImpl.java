package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.dto.ChatDto;
import com.alfabaykal.socialmediaapi.dto.ChatRequestDto;
import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.*;
import com.alfabaykal.socialmediaapi.repository.ChatRequestRepository;
import com.alfabaykal.socialmediaapi.service.ChatRequestService;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class ChatRequestServiceImpl implements ChatRequestService {

    private final ChatRequestRepository chatRequestRepository;
    private final ChatServiceImpl chatServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final EntityDtoConverter entityDtoConverter;

    public ChatRequestServiceImpl(ChatRequestRepository chatRequestRepository,
                                  ChatServiceImpl chatServiceImpl,
                                  UserServiceImpl userServiceImpl, EntityDtoConverter entityDtoConverter) {
        this.chatRequestRepository = chatRequestRepository;
        this.chatServiceImpl = chatServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.entityDtoConverter = entityDtoConverter;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void sendChatRequest(Long senderId, Long receiverId, Long chatId) {
        UserRelationshipDto sender = userServiceImpl.getUserByIdWithFriends(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userServiceImpl.getUserByIdWithFriends(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        if (alreadySent(sender, receiver, chatId))
            throw new MultipleChatRequestException();

        if (sender.getFriends().contains(receiver))
            chatRequestRepository
                    .save(entityDtoConverter
                            .convertChatRequestDtoToChatRequest
                                    (new ChatRequestDto(sender, receiver,
                                            chatId == null ? null : chatServiceImpl.getChatById(chatId)
                                                    .orElseThrow(() -> new ChatNotFoundException(chatId)))));
        else
            throw new NotFriendException();
    }

    @Transactional
    public void acceptChatRequest(Long chatRequestId, Long receiverId) {
        ChatRequestDto chatRequestDto = chatRequestRepository.findById(chatRequestId)
                .map(entityDtoConverter::convertChatRequestToChatRequestDto)
                .orElseThrow(() -> new ChatRequestNotFoundException(chatRequestId));
        UserDto sender = userServiceImpl.getUserById(chatRequestDto.getSender().getId())
                .orElseThrow(() -> new UserNotFoundException(chatRequestDto.getSender().getId()));
        UserDto receiver = userServiceImpl.getUserById(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        Long chatId = chatRequestDto.getChatDto() == null ? null :
                chatRequestDto.getChatDto().getId();

        ChatDto chatDto = chatId == null ? new ChatDto() :
                chatServiceImpl.getChatById(chatId)
                        .orElseThrow(() -> new ChatNotFoundException(chatId));

        if (chatId == null)
            chatDto.setUsers(Set.of(sender));

        Set<UserDto> chatUsers = new HashSet<>(chatDto.getUsers());

        if (!chatDto.getUsers().contains(receiver))
            chatUsers.add(receiver);

        chatDto.setUsers(chatUsers);

        chatRequestRepository.deleteById(chatRequestId);
        chatServiceImpl.save(chatDto);
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
