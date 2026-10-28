package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public final class EmployeeDTO {

    private EmployeeDTO() {
    }

    public record CreateEmployeeRequest(
            @NotBlank(message = "First name is required")
            String firstName,

            String middleName,

            @NotBlank(message = "Last name is required")
            String lastName,

            @NotBlank(message = "Email is required")
            @Email(message = "Invalid email format")
            String email,

            @NotBlank(message = "Team code is required")
            String teamCode,

            @NotBlank(message = "Designation is required")
            String designation,

            @NotBlank(message = "Manager employee code is required")
            String managerCode,

            String phone,
            String password,
            Employee.EmploymentStatus status,
            List<String> roles
    ) {
    }

    public record UpdateEmployeeRequest(
            String firstName,
            String middleName,
            String lastName,
            String email,
            String phone,
            String teamCode,
            String designation,
            String managerCode,
            Employee.EmploymentStatus status,
            List<String> roles
    ) {
    }
}
