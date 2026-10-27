package com.example.spring_test.controller;

import com.example.spring_test.services.EmployeeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee-roles")
@RequiredArgsConstructor
public class EmployeeRoleController {

    private final EmployeeRoleService employeeRoleService;

    @GetMapping("/init")
    public String init() {
        return employeeRoleService.init();
    }
}
