package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.RepeatedFriendshipRequestException;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.service.FriendshipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final UserServiceImpl userServiceImpl;

    public FriendshipServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        UserRelationshipDto sender = userServiceImpl.getUserByIdWithSubscriptionsAndSentFriendRequests(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userServiceImpl.getUserByIdWithSubscribersAndReceivedFriendRequests(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        if (sender.getSentFriendRequests().contains(receiver)) {
            throw new RepeatedFriendshipRequestException();
        }

        sender.getSubscriptions().add(receiver);
        sender.getSentFriendRequests().add(receiver);

        userServiceImpl.save(sender);

    }

    @Transactional
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        UserRelationshipDto sender = userServiceImpl.getUserByIdWithSubscribersAndSentFriendRequestsAndFriends(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userServiceImpl.getUserByIdWithSubscriptionsAndReceivedFriendRequestsAndFriends(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        sender.getSubscribers().add(receiver);
        sender.getFriends().add(receiver);
        sender.getSentFriendRequests().remove(receiver);

        receiver.getFriends().add(sender);

        userServiceImpl.save(sender);
        userServiceImpl.save(receiver);

    }

    @Transactional
    public void declineFriendRequest(Long senderId, Long receiverId) {
        UserRelationshipDto sender = userServiceImpl.getUserByIdWithSentFriendRequests(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        UserRelationshipDto receiver = userServiceImpl.getUserByIdWithReceivedFriendRequests(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        sender.getSentFriendRequests().remove(receiver);
        receiver.getReceivedFriendRequests().remove(sender);

        userServiceImpl.save(sender);
        userServiceImpl.save(receiver);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendToBeRemovedId) {
        UserRelationshipDto user = userServiceImpl.getUserByIdWithSubscriptionsAndFriends(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        UserRelationshipDto friendToBeRemoved = userServiceImpl.getUserByIdWithFriends(friendToBeRemovedId)
                .orElseThrow(() -> new UserNotFoundException(friendToBeRemovedId));

        user.getFriends().remove(friendToBeRemoved);
        user.getSubscriptions().remove(friendToBeRemoved);

        friendToBeRemoved.getFriends().remove(user);

        userServiceImpl.save(user);
        userServiceImpl.save(friendToBeRemoved);
    }

}
