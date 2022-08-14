package dev.mvvasilev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Refresh token expired.")
public class ExpiredRefreshTokenException extends MyApplicationException {
}
