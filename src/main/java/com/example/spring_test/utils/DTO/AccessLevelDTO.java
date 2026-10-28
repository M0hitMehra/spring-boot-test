package com.example.spring_test.utils.DTO;

import jakarta.validation.constraints.NotBlank;

public final class AccessLevelDTO {

    private AccessLevelDTO() {
    }

    public record CreateAccessLevelRequest(
            @NotBlank(message = "Access level name is required")
            String accessLevel,

            String description,
            Boolean active
    ) {
    }

    public record UpdateAccessLevelRequest(
            String accessLevel,
            String description,
            Boolean active
    ) {
    }
}
