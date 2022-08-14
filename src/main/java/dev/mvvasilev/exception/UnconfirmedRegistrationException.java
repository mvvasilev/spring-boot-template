package dev.mvvasilev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User has not confirmed their registration")
public class UnconfirmedRegistrationException extends MyApplicationException {
}
