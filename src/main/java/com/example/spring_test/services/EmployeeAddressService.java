package com.example.spring_test.services;

import com.example.spring_test.repositories.EmployeeAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeAddressService {

    private final EmployeeAddressRepository employeeAddressRepository;

    public String init() {
        return "EmployeeAddressService initialized";
    }
}
