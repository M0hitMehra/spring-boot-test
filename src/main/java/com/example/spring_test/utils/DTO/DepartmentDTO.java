package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Department;
import jakarta.validation.constraints.NotBlank;

public final class DepartmentDTO {

    private DepartmentDTO() {
    }

    public record CreateDepartmentRequest(
            @NotBlank(message = "Department name is required")
            String name,

            @NotBlank(message = "Branch code is required")
            String branchCode,

            String description,
            Department.Status status
    ) {
    }

    public record UpdateDepartmentRequest(
            String name,
            String branchCode,
            String description,
            Department.Status status
    ) {
    }
}
