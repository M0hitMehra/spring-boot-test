package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.TeamService;
import com.example.spring_test.utils.DTO.TeamDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createTeam(
            @Valid @RequestBody TeamDTO.CreateTeamRequest body
    ) {
        return teamService.createTeam(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{teamCode}")
    public ResponseEntity<ApiResponse<Object>> getTeamByCode(@PathVariable String teamCode) {
        return teamService.getTeamByCode(teamCode);
    }

    @PutMapping("/{teamCode}")
    public ResponseEntity<ApiResponse<Object>> updateTeam(
            @PathVariable String teamCode,
            @RequestBody TeamDTO.UpdateTeamRequest body
    ) {
        return teamService.updateTeam(teamCode, body);
    }

    @DeleteMapping("/{teamCode}")
    public ResponseEntity<ApiResponse<Object>> deleteTeam(@PathVariable String teamCode) {
        return teamService.deleteTeam(teamCode);
    }
}
