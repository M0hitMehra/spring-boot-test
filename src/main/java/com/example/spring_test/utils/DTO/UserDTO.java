package com.example.spring_test.utils.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public final class UserDTO {

    private UserDTO() {
    }

    public record SignupRequest(
            @NotBlank(message = "username required")
            String username,

            @NotBlank(message = "password required")
            String password,

            @NotBlank(message = "firstName required")
            String firstName,

            @NotBlank(message = "lastName required")
            String lastName,

            @NotBlank(message = "middleName required")
            String middleName,

            @NotBlank(message = "team id required")
            String team_id,

            @NotBlank(message = "designation id required")
            String designation_id,

            @NotBlank(message = "manager id required")
            String manager_id,

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