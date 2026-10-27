package com.example.spring_test.services;

import com.example.spring_test.repositories.EmployeeRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRoleService {

    private final EmployeeRoleRepository employeeRoleRepository;

    public String init() {
        return "EmployeeRoleService initialized";
    }
}
