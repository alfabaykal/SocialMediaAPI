package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.facade.FriendshipFacade;
import com.alfabaykal.socialmediaapi.service.FriendshipService;
import org.springframework.stereotype.Service;

@Service
public class FriendshipFacadeImpl implements FriendshipFacade {

    private final FriendshipService friendshipService;

    public FriendshipFacadeImpl(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @Override
    public void sendFriendRequest(Long senderId, Long receiverId) {
        friendshipService.sendFriendRequest(senderId, receiverId);
    }

    @Override
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        friendshipService.acceptFriendRequest(senderId, receiverId);
    }

    @Override
    public void declineFriendRequest(Long senderId, Long receiverId) {
        friendshipService.declineFriendRequest(senderId, receiverId);
    }

    @Override
    public void removeFriend(Long userId, Long friendToBeRemovedId) {
        friendshipService.removeFriend(userId, friendToBeRemovedId);
    }
}
