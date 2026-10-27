package com.example.spring_test.services;

import com.example.spring_test.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public String init() {
        return "EmployeeService initialized";
    }
}
