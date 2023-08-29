package com.alfabaykal.socialmediaapi.exception;

public class RepeatedFriendshipRequestException extends IllegalStateException {
    public RepeatedFriendshipRequestException() {
        super("Friendship request already sent");
    }
}
