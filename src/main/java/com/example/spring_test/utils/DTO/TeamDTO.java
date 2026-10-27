package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Team;
import jakarta.validation.constraints.NotBlank;

public final class TeamDTO {

    private TeamDTO() {
    }

    public record CreateTeamRequest(
            @NotBlank(message = "Team code is required")
            String code,

            @NotBlank(message = "Team name is required")
            String name,

            @NotBlank(message = "Department code is required")
            String departmentCode,

            String description,
            String leadCode,
            Team.Status status
    ) {
    }

    public record UpdateTeamRequest(
            String name,
            String departmentCode,
            String description,
            String leadCode,
            Team.Status status
    ) {
    }
}
