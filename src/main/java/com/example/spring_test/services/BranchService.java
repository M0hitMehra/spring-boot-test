package com.example.spring_test.services;

import com.example.spring_test.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public String init() {
        return "BranchService initialized";
    }
}
