package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Designation;
import jakarta.validation.constraints.NotBlank;

public final class DesignationDTO {

    private DesignationDTO() {
    }

    public record CreateDesignationRequest(
            @NotBlank(message = "Designation name is required")
            String name,

            Integer level,
            String description,
            Designation.Status status
    ) {
    }

    public record UpdateDesignationRequest(
            String name,
            Integer level,
            String description,
            Designation.Status status
    ) {
    }
}
