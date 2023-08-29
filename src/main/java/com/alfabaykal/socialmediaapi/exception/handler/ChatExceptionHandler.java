package com.alfabaykal.socialmediaapi.exception.handler;

import com.alfabaykal.socialmediaapi.exception.ChatNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ChatExceptionHandler {
    @ExceptionHandler(ChatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleChatNotFoundException(ChatNotFoundException e) {
        return e.getMessage();
    }
}
