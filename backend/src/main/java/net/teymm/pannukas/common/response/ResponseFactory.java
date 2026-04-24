package net.teymm.pannukas.common.response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public final class ResponseFactory {

    public static <T> ApiResponse<T> success(
            String message,
            T data,
            HttpStatus status,
            HttpServletRequest request
    ) {
        return new ApiResponse<>(
                request.getRequestURI(),
                status.value(),
                status.name(),
                message,
                data,
                null,
                Instant.now()
        );
    }

    public static <T> ApiResponse<T> error(
            String message,
            Object errors,
            HttpStatus status,
            HttpServletRequest request
    ) {
        return new ApiResponse<>(
                request.getRequestURI(),
                status.value(),
                status.name(),
                message,
                null,
                errors,
                Instant.now()
        );
    }
}
