package com.example.spring_test.services;

import com.example.spring_test.repositories.AccessLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessLevelService {

    private final AccessLevelRepository accessLevelRepository;

    public String init() {
        return "AccessLevelService initialized";
    }
}
