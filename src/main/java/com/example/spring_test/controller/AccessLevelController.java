package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.AccessLevelService;
import com.example.spring_test.utils.DTO.AccessLevelDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/access-levels")
@RequiredArgsConstructor
public class AccessLevelController {

    private final AccessLevelService accessLevelService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createAccessLevel(
            @Valid @RequestBody AccessLevelDTO.CreateAccessLevelRequest body
    ) {
        return accessLevelService.createAccessLevel(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllAccessLevels() {
        return accessLevelService.getAllAccessLevels();
    }

    @GetMapping("/{accessLevelCode}")
    public ResponseEntity<ApiResponse<Object>> getAccessLevelByCode(@PathVariable String accessLevelCode) {
        return accessLevelService.getAccessLevelByCode(accessLevelCode);
    }

    @PutMapping("/{accessLevelCode}")
    public ResponseEntity<ApiResponse<Object>> updateAccessLevel(
            @PathVariable String accessLevelCode,
            @RequestBody AccessLevelDTO.UpdateAccessLevelRequest body
    ) {
        return accessLevelService.updateAccessLevel(accessLevelCode, body);
    }

    @DeleteMapping("/{accessLevelCode}")
    public ResponseEntity<ApiResponse<Object>> deleteAccessLevel(@PathVariable String accessLevelCode) {
        return accessLevelService.deleteAccessLevel(accessLevelCode);
    }
}
