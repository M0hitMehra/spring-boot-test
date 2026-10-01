package com.example.spring_test.utils.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public final class UserDTO {

    private UserDTO() {
        // Utility wrapper class
    }

    public record SignupRequest(
            @NotBlank(message = "username required")
            String username,

            @NotBlank(message = "password required")
            String password,

            @NotBlank(message = "email required")
            @Email(message = "invalid email")
            String email
    ) {
    }

    public record SignupRequestWithRoles(
            @NotBlank(message = "username required")
            String username,

            @NotBlank(message = "password required")
            String password,

            @NotBlank(message = "email required")
            @Email(message = "invalid email")
            String email,

            @NotEmpty(message = "Roles cannot be empty")
            List<String> roles
    ) {
    }

    public record LoginRequest(
            String email,
            String password
    ) {
    }
}