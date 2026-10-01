package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    @PostMapping("/users/{greeting}")
    public ResponseEntity<ApiResponse<Map<String, String>>> greet(
            @PathVariable(required = false) @NotBlank String greeting
    ) {
        Map<String, String> m = Map.of("name", "Mohit", "salutation", greeting);
        return GlobalResponseHandler.success("Test running", m, HttpStatus.MULTI_STATUS);
    }
}
