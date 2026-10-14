package com.example.spring_test.controller;

import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.services.CompanyService;
import com.example.spring_test.utils.DTO.CompanyDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Object>> createCompany(
            @Valid @RequestBody CompanyDTO.CreateCompanyRequestBody body
    ) {
        return companyService.addCompany(body);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Object>> getCompanies(
            @ModelAttribute CompanyDTO.CompanySearchRequest filter,
            Pageable pageable
    ) {
        return companyService.getCompanies(filter, pageable);
    }
}
