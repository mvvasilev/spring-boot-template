package dev.mvvasilev.controller;

import dev.mvvasilev.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ApiController {

    default <T> ResponseEntity<ApiResponse<T>> ok(@NonNull T response) {
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    default <T> ResponseEntity<T> notFound() {
        return ResponseEntity.notFound().build();
    }

    default <T> ResponseEntity<T> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    default <T> ResponseEntity<ApiResponse<T>> response(@NonNull HttpStatus status, @Nullable T response) {
        return ResponseEntity.status(status).body(ApiResponse.of(response));
    }

}
