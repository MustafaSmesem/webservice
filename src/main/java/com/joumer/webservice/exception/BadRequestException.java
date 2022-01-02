package com.joumer.webservice.exception;

import com.joumer.webservice.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg) {
        super(msg);
        Log.error(msg);
    }
}
