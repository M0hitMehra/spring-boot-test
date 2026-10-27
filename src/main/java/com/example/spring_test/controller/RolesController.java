package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.RolesService;
import com.example.spring_test.utils.DTO.RoleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RolesService rolesService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createRole(
            @Valid @RequestBody RoleDTO.CreateRoleRequest body
    ) {
        return rolesService.createRole(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllRoles() {
        return rolesService.getAllRoles();
    }

    @GetMapping("/{roleCode}")
    public ResponseEntity<ApiResponse<Object>> getRoleByCode(@PathVariable String roleCode) {
        return rolesService.getRoleByCode(roleCode);
    }

    @PutMapping("/{roleCode}")
    public ResponseEntity<ApiResponse<Object>> updateRole(
            @PathVariable String roleCode,
            @RequestBody RoleDTO.UpdateRoleRequest body
    ) {
        return rolesService.updateRole(roleCode, body);
    }

    @DeleteMapping("/{roleCode}")
    public ResponseEntity<ApiResponse<Object>> deleteRole(@PathVariable String roleCode) {
        return rolesService.deleteRole(roleCode);
    }
}
