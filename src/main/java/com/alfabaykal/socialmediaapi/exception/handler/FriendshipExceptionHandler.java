package com.alfabaykal.socialmediaapi.exception.handler;

import com.alfabaykal.socialmediaapi.exception.RepeatedFriendshipRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FriendshipExceptionHandler {

    @ExceptionHandler(RepeatedFriendshipRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleRepeatedFriendshipRequest(RepeatedFriendshipRequestException e) {
        return e.getMessage();
    }
}
