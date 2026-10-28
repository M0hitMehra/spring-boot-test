package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.*;
import com.example.spring_test.repositories.BranchRepository;
import com.example.spring_test.repositories.CompanyRepository;
import com.example.spring_test.repositories.EmployeeRepository;
import com.example.spring_test.utils.DTO.BranchDTO;
import com.example.spring_test.utils.helpers.GlobalHelpers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createBranch(BranchDTO.CreateBranchRequest body) {
        Company company = companyRepository.findByCode(body.companyCode()).stream().findFirst()
                .orElseThrow(() -> new ApiException("COMPANY_NOT_FOUND", "Company not found with code: " + body.companyCode(), HttpStatus.UNPROCESSABLE_CONTENT));

        Employee manager = employeeRepository.findByEmployeeCode(body.managerCode())
                .orElseThrow(() -> new ApiException("MANAGER_NOT_FOUND", "Manager not found with employee code: " + body.managerCode(), HttpStatus.UNPROCESSABLE_CONTENT));

        String branchCode = GlobalHelpers.generateCode(body.name(), 4);
        while (branchRepository.findByCode(branchCode).isPresent()) {
            branchCode = GlobalHelpers.generateCode(body.name(), 4);
        }

        Address regAddress = Address.builder()
                .addressLine1("Registered Address")
                .city("Default")
                .state("Default")
                .country("India")
                .addressType(AddressType.REGISTERED)
                .build();

        Address permAddress = Address.builder()
                .addressLine1("Permanent Address")
                .city("Default")
                .state("Default")
                .country("India")
                .addressType(AddressType.PERMANENT)
                .build();

        Branch branch = Branch.builder()
                .code(branchCode)
                .name(body.name())
                .company(company)
                .manager(manager)
                .email(body.email())
                .phone(body.phone())
                .status(body.status() != null ? body.status() : Branch.Status.ACTIVE)
                .registeredAddress(regAddress)
                .permanentAddress(permAddress)
                .build();

        Branch savedBranch = branchRepository.save(branch);
        return GlobalResponseHandler.created("Branch created successfully", savedBranch);
    }

    public ResponseEntity<ApiResponse<Object>> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return GlobalResponseHandler.success("Branches fetched successfully", branches, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getBranchByCode(String branchCode) {
        Branch branch = branchRepository.findByCode(branchCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Branch not found with code: " + branchCode, HttpStatus.NOT_FOUND));

        return GlobalResponseHandler.success("Branch fetched successfully", branch, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateBranch(String branchCode, BranchDTO.UpdateBranchRequest body) {
        Branch branch = branchRepository.findByCode(branchCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Branch not found with code: " + branchCode, HttpStatus.NOT_FOUND));

        if (body.name() != null) branch.setName(body.name());
        if (body.email() != null) branch.setEmail(body.email());
        if (body.phone() != null) branch.setPhone(body.phone());
        if (body.status() != null) branch.setStatus(body.status());

        if (body.companyCode() != null && !body.companyCode().isBlank()) {
            Company company = companyRepository.findByCode(body.companyCode()).stream().findFirst()
                    .orElseThrow(() -> new ApiException("COMPANY_NOT_FOUND", "Company not found with code: " + body.companyCode(), HttpStatus.UNPROCESSABLE_CONTENT));
            branch.setCompany(company);
        }

        if (body.managerCode() != null && !body.managerCode().isBlank()) {
            Employee manager = employeeRepository.findByEmployeeCode(body.managerCode())
                    .orElseThrow(() -> new ApiException("MANAGER_NOT_FOUND", "Manager not found with employee code: " + body.managerCode(), HttpStatus.UNPROCESSABLE_CONTENT));
            branch.setManager(manager);
        }

        Branch updatedBranch = branchRepository.save(branch);
        return GlobalResponseHandler.success("Branch updated successfully", updatedBranch, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteBranch(String branchCode) {
        Branch branch = branchRepository.findByCode(branchCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Branch not found with code: " + branchCode, HttpStatus.NOT_FOUND));

        branchRepository.delete(branch);
        return GlobalResponseHandler.success("Branch deleted successfully", null, HttpStatus.OK);
    }
}
