package com.example.spring_test.services;

import com.example.spring_test.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public String init() {
        return "DepartmentService initialized";
    }
}
