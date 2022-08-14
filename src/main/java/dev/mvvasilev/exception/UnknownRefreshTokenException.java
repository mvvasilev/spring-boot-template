package dev.mvvasilev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Refresh token could not be associated with any known user.")
public class UnknownRefreshTokenException extends MyApplicationException {
}
