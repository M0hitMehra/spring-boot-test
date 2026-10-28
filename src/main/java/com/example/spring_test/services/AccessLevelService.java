package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Access_level;
import com.example.spring_test.repositories.AccessLevelRepository;
import com.example.spring_test.utils.DTO.AccessLevelDTO;
import com.example.spring_test.utils.helpers.GlobalHelpers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessLevelService {

    private final AccessLevelRepository accessLevelRepository;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createAccessLevel(AccessLevelDTO.CreateAccessLevelRequest body) {
        String accessLevelCode = "ACC_" + GlobalHelpers.generateCode(body.accessLevel(), 4);
        while (accessLevelRepository.findByAccessLevelCode(accessLevelCode) != null) {
            accessLevelCode = "ACC_" + GlobalHelpers.generateCode(body.accessLevel(), 4);
        }

        Access_level accessLevel = Access_level.builder()
                .accessLevelCode(accessLevelCode)
                .accessLevel(body.accessLevel())
                .description(body.description())
                .active(body.active() != null ? body.active() : true)
                .build();

        Access_level saved = accessLevelRepository.save(accessLevel);
        return GlobalResponseHandler.created("Access level created successfully", saved);
    }

    public ResponseEntity<ApiResponse<Object>> getAllAccessLevels() {
        List<Access_level> accessLevels = accessLevelRepository.findAll();
        return GlobalResponseHandler.success("Access levels fetched successfully", accessLevels, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getAccessLevelByCode(String accessLevelCode) {
        Access_level accessLevel = accessLevelRepository.findByAccessLevelCode(accessLevelCode);
        if (accessLevel == null) {
            throw new ApiException("NOT_FOUND", "Access level not found with code: " + accessLevelCode, HttpStatus.NOT_FOUND);
        }

        return GlobalResponseHandler.success("Access level fetched successfully", accessLevel, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateAccessLevel(String accessLevelCode, AccessLevelDTO.UpdateAccessLevelRequest body) {
        Access_level accessLevel = accessLevelRepository.findByAccessLevelCode(accessLevelCode);
        if (accessLevel == null) {
            throw new ApiException("NOT_FOUND", "Access level not found with code: " + accessLevelCode, HttpStatus.NOT_FOUND);
        }

        if (body.accessLevel() != null) accessLevel.setAccessLevel(body.accessLevel());
        if (body.description() != null) accessLevel.setDescription(body.description());
        if (body.active() != null) accessLevel.setActive(body.active());

        Access_level updated = accessLevelRepository.save(accessLevel);
        return GlobalResponseHandler.success("Access level updated successfully", updated, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteAccessLevel(String accessLevelCode) {
        Access_level accessLevel = accessLevelRepository.findByAccessLevelCode(accessLevelCode);
        if (accessLevel == null) {
            throw new ApiException("NOT_FOUND", "Access level not found with code: " + accessLevelCode, HttpStatus.NOT_FOUND);
        }

        accessLevelRepository.delete(accessLevel);
        return GlobalResponseHandler.success("Access level deleted successfully", null, HttpStatus.OK);
    }
}
