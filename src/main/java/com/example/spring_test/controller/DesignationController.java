package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.DesignationService;
import com.example.spring_test.utils.DTO.DesignationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/designations")
@RequiredArgsConstructor
public class DesignationController {

    private final DesignationService designationService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createDesignation(
            @Valid @RequestBody DesignationDTO.CreateDesignationRequest body
    ) {
        return designationService.createDesignation(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllDesignations() {
        return designationService.getAllDesignations();
    }

    @GetMapping("/{designationCode}")
    public ResponseEntity<ApiResponse<Object>> getDesignationByCode(@PathVariable String designationCode) {
        return designationService.getDesignationByCode(designationCode);
    }

    @PutMapping("/{designationCode}")
    public ResponseEntity<ApiResponse<Object>> updateDesignation(
            @PathVariable String designationCode,
            @RequestBody DesignationDTO.UpdateDesignationRequest body
    ) {
        return designationService.updateDesignation(designationCode, body);
    }

    @DeleteMapping("/{designationCode}")
    public ResponseEntity<ApiResponse<Object>> deleteDesignation(@PathVariable String designationCode) {
        return designationService.deleteDesignation(designationCode);
    }
}
