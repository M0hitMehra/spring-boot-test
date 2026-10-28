package com.example.spring_test.middlewares.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getStatus().value()
        );
        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                exception.toString(),
                500
        );
        return ResponseEntity
                .status(500)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        System.out.println(3);

        String message = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .orElse("Validation failed");
        System.out.println(4);

        ErrorResponse response = new ErrorResponse(
                "VALIDATION_ERROR",
                message,
                400
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}

