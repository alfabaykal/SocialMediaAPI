package com.alfabaykal.socialmediaapi.exception.handler;

import com.alfabaykal.socialmediaapi.exception.ChatRequestNotFoundException;
import com.alfabaykal.socialmediaapi.exception.MultipleChatRequestException;
import com.alfabaykal.socialmediaapi.exception.NotFriendException;
import com.alfabaykal.socialmediaapi.exception.NotYourChatRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ChatRequestExceptionHandler {
    @ExceptionHandler(ChatRequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleChatNotFoundException(ChatRequestNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotFriendException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleNotFriendException(NotFriendException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotYourChatRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleNotYourChatRequestException(NotYourChatRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MultipleChatRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleMultipleChatRequestException(MultipleChatRequestException e) {
        return e.getMessage();
    }
}
