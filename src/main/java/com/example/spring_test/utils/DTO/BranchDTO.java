package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Branch;
import jakarta.validation.constraints.NotBlank;

public final class BranchDTO {

    private BranchDTO() {
    }

    public record CreateBranchRequest(
            @NotBlank(message = "Branch name is required")
            String name,

            @NotBlank(message = "Company code is required")
            String companyCode,

            @NotBlank(message = "Manager employee code is required")
            String managerCode,

            String email,
            String phone,
            Branch.Status status
    ) {
    }

    public record UpdateBranchRequest(
            String name,
            String companyCode,
            String email,
            String phone,
            String managerCode,
            Branch.Status status
    ) {
    }
}
