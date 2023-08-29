package com.alfabaykal.socialmediaapi.exception;

public class NotYourChatRequestException extends RuntimeException {
    public NotYourChatRequestException() {
        super("This is not your chat request");
    }
}
