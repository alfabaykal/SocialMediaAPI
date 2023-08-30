package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    Long getIdByUsername(String username);

    Optional<UserDto> getUserById(Long id);

    Optional<UserRelationshipDto> getUserByIdWithFriends(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscriptions(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscribers(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSentFriendRequests(Long id);

    Optional<UserRelationshipDto> getUserByIdWithReceivedFriendRequests(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscriptionsAndSentFriendRequests(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscribersAndReceivedFriendRequests(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(Long id);

    Optional<UserRelationshipDto> getUserByIdWithSubscriptionsAndFriends(Long id);

    void save(UserRelationshipDto userRelationshipDto);

    void save(UserRegistrationDto userRegistrationDto);

    UserDto updateUser(Long id, UserRegistrationDto userRegistrationDto);

    void deleteUser(Long id);

}
