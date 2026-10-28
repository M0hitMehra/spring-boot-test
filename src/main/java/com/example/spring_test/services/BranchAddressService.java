package com.example.spring_test.services;

import com.example.spring_test.repositories.BranchAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchAddressService {

    private final BranchAddressRepository branchAddressRepository;

    public String init() {
        return "BranchAddressService initialized";
    }
}
