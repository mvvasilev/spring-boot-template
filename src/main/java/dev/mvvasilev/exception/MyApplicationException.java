package dev.mvvasilev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class MyApplicationException extends RuntimeException {
    public MyApplicationException() {
    }

    public MyApplicationException(String message) {
        super(message);
    }

    public MyApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
