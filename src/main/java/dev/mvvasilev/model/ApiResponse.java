package dev.mvvasilev.model;

import java.util.List;

public final class ApiResponse<T> {

    private final T result;
    private final boolean success;
    private final List<Object> errors;

    public static <T> ApiResponse<T> of(T result) {
        return new ApiResponse<T>(result, true, (Exception) null);
    }

    public static ApiResponse<Void> error(Exception e) {
        return new ApiResponse<>(null, false, e);
    }

    public static ApiResponse<Void> errors(Object... errors) {
        return new ApiResponse<>(null, false, List.of(errors));
    }

    protected ApiResponse(T result, boolean success, Exception error) {
        this.result = result;
        this.success = success;
        this.errors = error == null ? null : List.of(error.toString());
    }

    protected ApiResponse(T result, boolean success, List<Object> errors) {
        this.result = result;
        this.success = success;
        this.errors = errors;
    }

    public T getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Object> getErrors() {
        return errors;
    }
}
