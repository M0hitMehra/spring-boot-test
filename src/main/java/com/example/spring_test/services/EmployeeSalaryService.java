package com.example.spring_test.services;

import com.example.spring_test.repositories.EmployeeSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeSalaryService {

    private final EmployeeSalaryRepository employeeSalaryRepository;

    public String init() {
        return "EmployeeSalaryService initialized";
    }
}
