package com.example.spring_test.controller;

import com.example.spring_test.services.BranchAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/branch-addresses")
@RequiredArgsConstructor
public class BranchAddressController {

    private final BranchAddressService branchAddressService;

    @GetMapping("/init")
    public String init() {
        return branchAddressService.init();
    }
}
