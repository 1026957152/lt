package com.lt.dom.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private final long userId;
    public UserNotFoundException(long userId) {
        super(String.format("Could not find user with id %s", userId));
        this.userId = userId;
    }
/*    @ResponseErrorProperty
    public String getUserId() {
        return userId.getValue();
    }*/
}