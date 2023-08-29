package com.alfabaykal.socialmediaapi.exception;

public class MultipleChatRequestException extends RuntimeException {
    public MultipleChatRequestException() {
        super("You can only send one request to a person at a time");
    }
}
