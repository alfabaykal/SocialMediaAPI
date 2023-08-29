package com.alfabaykal.socialmediaapi.exception.handler;

import com.alfabaykal.socialmediaapi.exception.LoginFailedException;
import com.alfabaykal.socialmediaapi.exception.UserNotCreatedException;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.exception.UserNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleUserNotFound(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleUserNotCreated(UserNotCreatedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleUserNotCreated(UserNotUpdatedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleLoginFailedException(LoginFailedException e) {
        return e.getMessage();
    }
}

