package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Long getUserIdByUsername(String username);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByIdWithFriends(Long id);

    Optional<User> getUserByIdWithSubscriptions(Long id);

    Optional<User> getUserByIdWithSubscribers(Long id);

    Optional<User> getUserByIdWithSentFriendRequests(Long id);

    Optional<User> getUserByIdWithReceivedFriendRequests(Long id);

    Optional<User> getUserByIdWithSubscriptionsAndSentFriendRequests(Long id);

    Optional<User> getUserByIdWithSubscribersAndReceivedFriendRequests(Long id);

    Optional<User> getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(Long id);

    Optional<User> getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(Long id);

    Optional<User> getUserByIdWithSubscriptionsAndFriends(Long id);

//    void save(UserRelationshipDto userRelationshipDto);

    void save(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

}
