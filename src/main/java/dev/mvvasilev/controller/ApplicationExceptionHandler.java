package dev.mvvasilev.controller;

import dev.mvvasilev.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public final class ApplicationExceptionHandler {

    private Logger logger = LoggerFactory.getLogger("ExceptionHandler");

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAny(Exception e) {
        ResponseStatus annotation = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return ResponseEntity.status(annotation.code()).body(ApiResponse.errors(annotation.reason()));
        }

        logger.error("Unhandled Exception: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e));
    }

    @ExceptionHandler(value = DbActionExecutionException.class)
    public ResponseEntity<ApiResponse<Void>> handleSqlException(DbActionExecutionException e) {
        if (e.getCause() instanceof DuplicateKeyException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.errors("One or more of the provided values already exist and conflict with others."));
        } else {
            throw e;
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream()
                .map(fe -> String.format("Invalid field value for '%s.%s': %s", fe.getObjectName(), fe.getField(), fe.getDefaultMessage()))
                .toArray();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.errors(errors));
    }
}
