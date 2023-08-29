package com.alfabaykal.socialmediaapi.exception;

public class ChatRequestNotFoundException extends RuntimeException {
    public ChatRequestNotFoundException(Long id) {
        super("Chat request with id " + id + " not found");
    }
}
