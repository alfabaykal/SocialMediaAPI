package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.exception.RepeatedFriendshipRequestException;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.model.User;
import com.alfabaykal.socialmediaapi.service.FriendshipService;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final UserService userService;

    public FriendshipServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        User sender = userService.getUserByIdWithSubscriptionsAndSentFriendRequests(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = userService.getUserByIdWithSubscribersAndReceivedFriendRequests(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        if (sender.getSentFriendRequests().contains(receiver)) {
            throw new RepeatedFriendshipRequestException();
        }

        sender.getSubscriptions().add(receiver);
        sender.getSentFriendRequests().add(receiver);

        userService.save(sender);

    }

    @Transactional
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        User sender = userService.getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = userService.getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        sender.getSubscribers().add(receiver);
        sender.getFriends().add(receiver);
        sender.getSentFriendRequests().remove(receiver);

        receiver.getFriends().add(sender);

        userService.save(sender);
        userService.save(receiver);

    }

    @Transactional
    public void declineFriendRequest(Long senderId, Long receiverId) {
        User sender = userService.getUserByIdWithSentFriendRequests(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = userService.getUserByIdWithReceivedFriendRequests(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        sender.getSentFriendRequests().remove(receiver);
        receiver.getReceivedFriendRequests().remove(sender);

        userService.save(sender);
        userService.save(receiver);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendToBeRemovedId) {
        User user = userService.getUserByIdWithSubscriptionsAndFriends(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User friendToBeRemoved = userService.getUserByIdWithFriends(friendToBeRemovedId)
                .orElseThrow(() -> new UserNotFoundException(friendToBeRemovedId));

        user.getFriends().remove(friendToBeRemoved);
        user.getSubscriptions().remove(friendToBeRemoved);

        friendToBeRemoved.getFriends().remove(user);

        userService.save(user);
        userService.save(friendToBeRemoved);
    }

}
