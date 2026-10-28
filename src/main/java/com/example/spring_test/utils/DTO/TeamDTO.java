package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Team;
import jakarta.validation.constraints.NotBlank;

public final class TeamDTO {

    private TeamDTO() {
    }

    public record CreateTeamRequest(
            @NotBlank(message = "Team name is required")
            String name,

            @NotBlank(message = "Department code is required")
            String departmentCode,

            @NotBlank(message = "Lead employee code is required")
            String leadCode,

            String description,
            Team.Status status
    ) {
    }

    public record UpdateTeamRequest(
            String name,
            String departmentCode,
            String leadCode,
            String description,
            Team.Status status
    ) {
    }
}
