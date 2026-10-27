package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.DepartmentService;
import com.example.spring_test.utils.DTO.DepartmentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createDepartment(
            @Valid @RequestBody DepartmentDTO.CreateDepartmentRequest body
    ) {
        return departmentService.createDepartment(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{departmentCode}")
    public ResponseEntity<ApiResponse<Object>> getDepartmentByCode(@PathVariable String departmentCode) {
        return departmentService.getDepartmentByCode(departmentCode);
    }

    @PutMapping("/{departmentCode}")
    public ResponseEntity<ApiResponse<Object>> updateDepartment(
            @PathVariable String departmentCode,
            @RequestBody DepartmentDTO.UpdateDepartmentRequest body
    ) {
        return departmentService.updateDepartment(departmentCode, body);
    }

    @DeleteMapping("/{departmentCode}")
    public ResponseEntity<ApiResponse<Object>> deleteDepartment(@PathVariable String departmentCode) {
        return departmentService.deleteDepartment(departmentCode);
    }
}
