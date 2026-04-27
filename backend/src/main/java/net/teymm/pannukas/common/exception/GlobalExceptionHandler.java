package net.teymm.pannukas.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.teymm.pannukas.common.response.ApiResponse;
import net.teymm.pannukas.common.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, List<String>> groupedDetails = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    groupedDetails.putIfAbsent(error.getField(), new ArrayList<>());
                    groupedDetails.get(error.getField()).add(error.getDefaultMessage());
                });

        List<FieldValidationError> details = groupedDetails.entrySet()
                .stream()
                .map(entry -> new FieldValidationError(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiResponse<Void> response = ResponseFactory.error(
                "Validation failed",
                details,
                status,
                request
        );

        return new ResponseEntity<>(response, status);
    }

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
