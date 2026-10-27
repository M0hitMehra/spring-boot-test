package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.EmployeeService;
import com.example.spring_test.utils.DTO.EmployeeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createEmployee(
            @Valid @RequestBody EmployeeDTO.CreateEmployeeRequest body
    ) {
        return employeeService.createEmployee(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<ApiResponse<Object>> getEmployeeByCode(@PathVariable String employeeCode) {
        return employeeService.getEmployeeByCode(employeeCode);
    }

    @PutMapping("/{employeeCode}")
    public ResponseEntity<ApiResponse<Object>> updateEmployee(
            @PathVariable String employeeCode,
            @RequestBody EmployeeDTO.UpdateEmployeeRequest body
    ) {
        return employeeService.updateEmployee(employeeCode, body);
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<ApiResponse<Object>> deleteEmployee(@PathVariable String employeeCode) {
        return employeeService.deleteEmployee(employeeCode);
    }
}
