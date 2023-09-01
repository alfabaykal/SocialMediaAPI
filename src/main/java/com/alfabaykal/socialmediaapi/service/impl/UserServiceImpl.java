package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.model.User;
import com.alfabaykal.socialmediaapi.repository.ChatUsersRepository;
import com.alfabaykal.socialmediaapi.repository.FriendshipRepository;
import com.alfabaykal.socialmediaapi.repository.UserRepository;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChatUsersRepository chatUsersRepository;

    public UserServiceImpl(UserRepository userRepository,
                           FriendshipRepository friendshipRepository, ChatUsersRepository chatUsersRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.chatUsersRepository = chatUsersRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().toList();
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)).getId();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByIdWithFriends(Long id) {
        return userRepository.findWithFriendsById(id);
    }

    public Optional<User> getUserByIdWithSubscriptions(Long id) {
        return userRepository.findWithSubscriptionsById(id);
    }

    public Optional<User> getUserByIdWithSubscribers(Long id) {
        return userRepository.findWithSubscribersById(id);
    }

    public Optional<User> getUserByIdWithSentFriendRequests(Long id) {
        return userRepository.findWithSentFriendRequestsById(id);
    }

    public Optional<User> getUserByIdWithReceivedFriendRequests(Long id) {
        return userRepository.findWithReceivedFriendRequestsById(id);
    }

    public Optional<User> getUserByIdWithSubscriptionsAndSentFriendRequests(Long id) {
        return userRepository.findWithSubscriptionsAndSentFriendRequestsById(id);
    }

    public Optional<User> getUserByIdWithSubscribersAndReceivedFriendRequests(Long id) {
        return userRepository.findWithSubscribersAndReceivedFriendRequestsById(id);
    }

    public Optional<User> getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(Long id) {
        return userRepository.findWithSubscribersAndSentFriendRequestsAndFriendsById(id);
    }

    public Optional<User> getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(Long id) {
        return userRepository.findWithSubscriptionsAndReceivedFriendRequestsAndFriendsById(id);
    }

    public Optional<User> getUserByIdWithSubscriptionsAndFriends(Long id) {
        return userRepository.findWithSubscriptionsAndFriendsById(id);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User user) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());

        return userRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            friendshipRepository.deleteAllByUserId(id);
            chatUsersRepository.deleteAllByUserId(id);
            userRepository.deleteById(id);
        } else
            throw new UserNotFoundException(id);
    }

}
