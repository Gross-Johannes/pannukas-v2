package net.teymm.pannukas.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.teymm.pannukas.common.response.ApiResponse;
import net.teymm.pannukas.common.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(
            ApiException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = ex.getStatus();

        ApiResponse<Void> response = ResponseFactory.error(
                ex.getMessage(),
                null,
                status,
                request
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiResponse<Void> response = ResponseFactory.error(
                ex.getMessage(),
                null,
                status,
                request
        );

        return new ResponseEntity<>(response, status);
    }
}
