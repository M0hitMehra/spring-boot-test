package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.BranchService;
import com.example.spring_test.utils.DTO.BranchDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> createBranch(
            @Valid @RequestBody BranchDTO.CreateBranchRequest body
    ) {
        return branchService.createBranch(body);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ApiResponse<Object>> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping("/{branchCode}")
    public ResponseEntity<ApiResponse<Object>> getBranchByCode(@PathVariable String branchCode) {
        return branchService.getBranchByCode(branchCode);
    }

    @PutMapping("/{branchCode}")
    public ResponseEntity<ApiResponse<Object>> updateBranch(
            @PathVariable String branchCode,
            @RequestBody BranchDTO.UpdateBranchRequest body
    ) {
        return branchService.updateBranch(branchCode, body);
    }

    @DeleteMapping("/{branchCode}")
    public ResponseEntity<ApiResponse<Object>> deleteBranch(@PathVariable String branchCode) {
        return branchService.deleteBranch(branchCode);
    }
}
