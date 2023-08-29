package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.repository.UserRepository;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final EntityDtoConverter entityDtoConverter;

    public UserService(UserRepository userRepository, EntityDtoConverter entityDtoConverter) {
        this.userRepository = userRepository;
        this.entityDtoConverter = entityDtoConverter;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(entityDtoConverter::convertUserToUserDto).toList();
    }

    public Long getIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)).getId();
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(entityDtoConverter::convertUserToUserDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithFriends(Long id) {
        return userRepository.findWithFriendsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscriptions(Long id) {
        return userRepository.findWithSubscriptionsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscribers(Long id) {
        return userRepository.findWithSubscribersById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSentFriendRequests(Long id) {
        return userRepository.findWithSentFriendRequestsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithReceivedFriendRequests(Long id) {
        return userRepository.findWithReceivedFriendRequestsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscriptionsAndSentFriendRequests(Long id) {
        return userRepository.findWithSubscriptionsAndSentFriendRequestsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscribersAndReceivedFriendRequests(Long id) {
        return userRepository.findWithSubscribersAndReceivedFriendRequestsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(Long id) {
        return userRepository.findWithSubscribersAndSentFriendRequestsAndFriendsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(Long id) {
        return userRepository.findWithSubscriptionsAndReceivedFriendRequestsAndFriendsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    public Optional<UserRelationshipDto> getUserByIdWithSubscriptionsAndFriends(Long id) {
        return userRepository.findWithSubscriptionsAndFriendsById(id)
                .map(entityDtoConverter::convertUserToUserRelationshipDto);
    }

    @Transactional
    public void save(UserRelationshipDto userRelationshipDto) {
        userRepository.save(entityDtoConverter.convertUserRelationshipDtoToUser(userRelationshipDto));
    }

    @Transactional
    public void save(UserRegistrationDto userRegistrationDto) {
        userRepository.save(entityDtoConverter.convertUserRegistrationDtoToUser(userRegistrationDto));
    }

    @Transactional
    public UserDto updateUser(Long id, UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsById(id))
            userRegistrationDto.setId(id);

        return entityDtoConverter
                .convertUserToUserDto(userRepository.save(entityDtoConverter
                        .convertUserRegistrationDtoToUser(userRegistrationDto)));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }

}
