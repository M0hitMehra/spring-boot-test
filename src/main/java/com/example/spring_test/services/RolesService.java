package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Roles;
import com.example.spring_test.repositories.RolesRepository;
import com.example.spring_test.utils.DTO.RoleDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createRole(RoleDTO.CreateRoleRequest body) {
        if (rolesRepository.findByRoleCode(body.roleCode()) != null) {
            throw new ApiException("ALREADY_EXISTS", "Role already exists with code: " + body.roleCode(), HttpStatus.CONFLICT);
        }

        Roles role = Roles.builder()
                .roleCode(body.roleCode())
                .roleName(body.roleName())
                .description(body.description())
                .isActive(body.isActive() != null ? body.isActive() : true)
                .build();

        Roles saved = rolesRepository.save(role);
        return GlobalResponseHandler.created("Role created successfully", saved);
    }

    public ResponseEntity<ApiResponse<Object>> getAllRoles() {
        List<Roles> roles = rolesRepository.findAll();
        return GlobalResponseHandler.success("Roles fetched successfully", roles, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getRoleByCode(String roleCode) {
        Roles role = rolesRepository.findByRoleCode(roleCode);
        if (role == null) {
            throw new ApiException("NOT_FOUND", "Role not found with code: " + roleCode, HttpStatus.NOT_FOUND);
        }

        return GlobalResponseHandler.success("Role fetched successfully", role, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateRole(String roleCode, RoleDTO.UpdateRoleRequest body) {
        Roles role = rolesRepository.findByRoleCode(roleCode);
        if (role == null) {
            throw new ApiException("NOT_FOUND", "Role not found with code: " + roleCode, HttpStatus.NOT_FOUND);
        }

        if (body.roleName() != null) role.setRoleName(body.roleName());
        if (body.description() != null) role.setDescription(body.description());
        if (body.isActive() != null) role.setActive(body.isActive());

        Roles updated = rolesRepository.save(role);
        return GlobalResponseHandler.success("Role updated successfully", updated, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteRole(String roleCode) {
        Roles role = rolesRepository.findByRoleCode(roleCode);
        if (role == null) {
            throw new ApiException("NOT_FOUND", "Role not found with code: " + roleCode, HttpStatus.NOT_FOUND);
        }

        rolesRepository.delete(role);
        return GlobalResponseHandler.success("Role deleted successfully", null, HttpStatus.OK);
    }
}
