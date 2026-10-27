package com.example.spring_test.controller;

import com.example.spring_test.services.EmployeeAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee-addresses")
@RequiredArgsConstructor
public class EmployeeAddressController {

    private final EmployeeAddressService employeeAddressService;

    @GetMapping("/init")
    public String init() {
        return employeeAddressService.init();
    }
}
