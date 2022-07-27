package com.bafoly.ex.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NothingFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NothingFoundException(String message){
        super(message);
    }
}
