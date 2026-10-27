package com.example.spring_test.utils.DTO;

import jakarta.validation.constraints.NotBlank;

public final class RoleDTO {

    private RoleDTO() {
    }

    public record CreateRoleRequest(
            @NotBlank(message = "Role code is required")
            String roleCode,

            @NotBlank(message = "Role name is required")
            String roleName,

            String description,
            Boolean isActive
    ) {
    }

    public record UpdateRoleRequest(
            String roleName,
            String description,
            Boolean isActive
    ) {
    }
}
