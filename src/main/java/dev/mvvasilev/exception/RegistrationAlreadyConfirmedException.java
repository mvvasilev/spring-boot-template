package dev.mvvasilev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Registration has already been confirmed")
public class RegistrationAlreadyConfirmedException extends MyApplicationException {
}
