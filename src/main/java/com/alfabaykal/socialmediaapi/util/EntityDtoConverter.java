package com.alfabaykal.socialmediaapi.util;

import com.alfabaykal.socialmediaapi.dto.*;
import com.alfabaykal.socialmediaapi.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoConverter {

    private final ModelMapper modelMapper;

    public EntityDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(User.class, UserDto.class);
        modelMapper.createTypeMap(UserDto.class, User.class);

        modelMapper.createTypeMap(User.class, UserRelationshipDto.class);
        modelMapper.createTypeMap(UserRelationshipDto.class, User.class);

        modelMapper.createTypeMap(Chat.class, ChatDto.class);
        modelMapper.createTypeMap(ChatDto.class, Chat.class);

        modelMapper.createTypeMap(Message.class, MessageDto.class);
        modelMapper.createTypeMap(MessageDto.class, Message.class);
    }

    public User convertUserDtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public User convertUserRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
        return modelMapper.map(userRegistrationDto, User.class);
    }

    public UserRegistrationDto convertUserDtoToUserRegistrationDto(UserDto userDto) {
        return modelMapper.map(userDto, UserRegistrationDto.class);
    }

    public UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public UserRelationshipDto convertUserToUserRelationshipDto(User user) {
        return modelMapper.map(user, UserRelationshipDto.class);
    }

    public User convertUserRelationshipDtoToUser(UserRelationshipDto userRelationshipDto) {
        return modelMapper.map(userRelationshipDto, User.class);
    }

    public Post convertPostDtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    public PostDto convertPostToPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public Chat convertChatDtoToChat(ChatDto chatDto) {
        return modelMapper.map(chatDto, Chat.class);
    }

    public ChatDto convertChatToChatDto(Chat chat) {
        return modelMapper.map(chat, ChatDto.class);
    }

    public ChatRequest convertChatRequestDtoToChatRequest(ChatRequestDto chatRequestDto) {
        return modelMapper.map(chatRequestDto, ChatRequest.class);
    }

    public ChatRequestDto convertChatRequestToChatRequestDto(ChatRequest chatRequest) {
        return modelMapper.map(chatRequest, ChatRequestDto.class);
    }

}
