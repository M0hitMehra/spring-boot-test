package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Department;
import com.example.spring_test.models.Employee;
import com.example.spring_test.models.Team;
import com.example.spring_test.repositories.DepartmentRepository;
import com.example.spring_test.repositories.EmployeeRepository;
import com.example.spring_test.repositories.TeamRepository;
import com.example.spring_test.utils.DTO.TeamDTO;
import com.example.spring_test.utils.helpers.GlobalHelpers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createTeam(TeamDTO.CreateTeamRequest body) {
        Department department = departmentRepository.findByCode(body.departmentCode())
                .orElseThrow(() -> new ApiException("DEPARTMENT_NOT_FOUND", "Department not found with code: " + body.departmentCode(), HttpStatus.UNPROCESSABLE_CONTENT));

        Employee lead = employeeRepository.findByEmployeeCode(body.leadCode())
                .orElseThrow(() -> new ApiException("LEAD_NOT_FOUND", "Lead employee not found with code: " + body.leadCode(), HttpStatus.UNPROCESSABLE_CONTENT));

        String teamCode = GlobalHelpers.generateCode(body.name(), 4);
        while (teamRepository.findByCode(teamCode).isPresent()) {
            teamCode = GlobalHelpers.generateCode(body.name(), 4);
        }

        Team team = Team.builder()
                .code(teamCode)
                .name(body.name())
                .department(department)
                .description(body.description())
                .lead(lead)
                .status(body.status() != null ? body.status() : Team.Status.ACTIVE)
                .build();

        Team saved = teamRepository.save(team);
        return GlobalResponseHandler.created("Team created successfully", saved);
    }

    public ResponseEntity<ApiResponse<Object>> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return GlobalResponseHandler.success("Teams fetched successfully", teams, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getTeamByCode(String teamCode) {
        Team team = teamRepository.findByCode(teamCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Team not found with code: " + teamCode, HttpStatus.NOT_FOUND));

        return GlobalResponseHandler.success("Team fetched successfully", team, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateTeam(String teamCode, TeamDTO.UpdateTeamRequest body) {
        Team team = teamRepository.findByCode(teamCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Team not found with code: " + teamCode, HttpStatus.NOT_FOUND));

        if (body.name() != null) team.setName(body.name());
        if (body.description() != null) team.setDescription(body.description());
        if (body.status() != null) team.setStatus(body.status());

        if (body.departmentCode() != null && !body.departmentCode().isBlank()) {
            Department department = departmentRepository.findByCode(body.departmentCode())
                    .orElseThrow(() -> new ApiException("DEPARTMENT_NOT_FOUND", "Department not found with code: " + body.departmentCode(), HttpStatus.UNPROCESSABLE_CONTENT));
            team.setDepartment(department);
        }

        if (body.leadCode() != null && !body.leadCode().isBlank()) {
            Employee lead = employeeRepository.findByEmployeeCode(body.leadCode())
                    .orElseThrow(() -> new ApiException("LEAD_NOT_FOUND", "Lead employee not found with code: " + body.leadCode(), HttpStatus.UNPROCESSABLE_CONTENT));
            team.setLead(lead);
        }

        Team updated = teamRepository.save(team);
        return GlobalResponseHandler.success("Team updated successfully", updated, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteTeam(String teamCode) {
        Team team = teamRepository.findByCode(teamCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Team not found with code: " + teamCode, HttpStatus.NOT_FOUND));

        teamRepository.delete(team);
        return GlobalResponseHandler.success("Team deleted successfully", null, HttpStatus.OK);
    }
}
