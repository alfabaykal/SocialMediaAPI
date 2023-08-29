package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.RepeatedFriendshipRequestException;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipService {

    private final UserService userService;

    public FriendshipService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        UserRelationshipDto sender = userService.getUserByIdWithSubscriptionsAndSentFriendRequests(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userService.getUserByIdWithSubscribersAndReceivedFriendRequests(receiverId)
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
        UserRelationshipDto sender = userService.getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userService.getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(receiverId)
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
        UserRelationshipDto sender = userService.getUserByIdWithSentFriendRequests(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userService.getUserByIdWithReceivedFriendRequests(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        sender.getSentFriendRequests().remove(receiver);
        receiver.getReceivedFriendRequests().remove(sender);

        userService.save(sender);
        userService.save(receiver);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendToBeRemovedId) {
        UserRelationshipDto user = userService.getUserByIdWithSubscriptionsAndFriends(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        UserRelationshipDto friendToBeRemoved = userService.getUserByIdWithFriends(friendToBeRemovedId)
                .orElseThrow(() -> new UserNotFoundException(friendToBeRemovedId));

        user.getFriends().remove(friendToBeRemoved);
        user.getSubscriptions().remove(friendToBeRemoved);

        friendToBeRemoved.getFriends().remove(user);

        userService.save(user);
        userService.save(friendToBeRemoved);
    }
}
