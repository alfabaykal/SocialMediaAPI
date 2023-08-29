package com.alfabaykal.socialmediaapi.exception.handler;

import com.alfabaykal.socialmediaapi.exception.NotYourPostException;
import com.alfabaykal.socialmediaapi.exception.PostNotCreatedException;
import com.alfabaykal.socialmediaapi.exception.PostNotFoundException;
import com.alfabaykal.socialmediaapi.exception.PostNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PostExceptionHandler {
    @ExceptionHandler(NotYourPostException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleNotYourPostException(NotYourPostException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handlePostNotFoundException(PostNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PostNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlePostNotCreatedException(PostNotCreatedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PostNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlePostNotUpdatedException(PostNotUpdatedException e) {
        return e.getMessage();
    }
}
