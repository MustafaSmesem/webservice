package com.joumer.webservice.exception;

import com.joumer.webservice.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedRequestException extends RuntimeException {

    public UnAuthorizedRequestException(String msg) {
        super(msg);
        Log.error(msg);
    }
}
