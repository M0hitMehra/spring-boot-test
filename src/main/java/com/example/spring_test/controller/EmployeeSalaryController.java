package com.example.spring_test.controller;

import com.example.spring_test.services.EmployeeSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee-salaries")
@RequiredArgsConstructor
public class EmployeeSalaryController {

    private final EmployeeSalaryService employeeSalaryService;

    @GetMapping("/init")
    public String init() {
        return employeeSalaryService.init();
    }
}
