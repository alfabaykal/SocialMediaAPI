package com.alfabaykal.socialmediaapi.service;

public interface FriendshipService {

    void sendFriendRequest(Long senderId, Long receiverId);

    void acceptFriendRequest(Long senderId, Long receiverId);

    void declineFriendRequest(Long senderId, Long receiverId);

    void removeFriend(Long userId, Long friendToBeRemovedId);
}
