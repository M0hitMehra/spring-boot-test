package com.example.spring_test.services;

import com.example.spring_test.repositories.DesignationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DesignationService {

    private final DesignationRepository designationRepository;

    public String init() {
        return "DesignationService initialized";
    }
}
