package com.example.spring_test.services;

import com.example.spring_test.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;

    public String init() {
        return "RolesService initialized";
    }
}
