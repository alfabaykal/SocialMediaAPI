package com.alfabaykal.socialmediaapi.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId) {
        super("Post with id " + postId + " not found");
    }
}
