package com.alfabaykal.socialmediaapi.exception;

public class NotFriendException extends RuntimeException {
    public NotFriendException() {
        super("Only friends can send chat invites");
    }

}
