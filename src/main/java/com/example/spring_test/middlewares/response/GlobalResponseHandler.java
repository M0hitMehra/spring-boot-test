package com.example.spring_test.middlewares.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponseHandler {

    public static <T> ResponseEntity<ApiResponse<T>> success(
            String message,
            T data,
            HttpStatus httpStatus
    ) {

        ApiResponse<T> response =
                new ApiResponse<>(true, message, data);

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(
            String message,
            T data
    ) {

        ApiResponse<T> response =
                new ApiResponse<>(true, message, data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(
            String message
    ) {

        ApiResponse<T> response =
                new ApiResponse<>(true, message, null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
