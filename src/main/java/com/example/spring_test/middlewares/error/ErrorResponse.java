package com.example.spring_test.middlewares.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

    private String errorCode;
    private String message;
    private int statusCode;


}
