package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Address;
import com.example.spring_test.models.AddressType;
import com.example.spring_test.models.Company;
import com.example.spring_test.repositories.CompanyRepository;
import com.example.spring_test.utils.DTO.CompanyDTO;
import com.example.spring_test.utils.DTOResolver.CompanyDTOResolver;
import com.example.spring_test.utils.helpers.GlobalHelpers;
import com.example.spring_test.utils.query.CompanySpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> addCompany(CompanyDTO.CreateCompanyRequestBody body) {

        String companyCode = GlobalHelpers.generateCode(body.companyName(), 4);

        Address registeredAddress = CompanyDTOResolver.toEntity(body.registeredAddress(), AddressType.REGISTERED);
        Address permanentAddress = CompanyDTOResolver.toEntity(body.permanentAddress(), AddressType.PERMANENT);
        Address currentAddress = body.currentAddress() != null
                ? CompanyDTOResolver.toEntity(body.currentAddress(), AddressType.CURRENT) : null;
        Address officeAddress = body.officeAddress() != null
                ? CompanyDTOResolver.toEntity(body.officeAddress(), AddressType.OFFICE) : null;

        Company company = Company.builder()
                .name(body.companyName())
                .legalName(body.legalName())
                .code(companyCode)
                .email(body.email())
                .phone(body.phone())
                .gstin(body.gstin())
                .registeredAddress(registeredAddress)
                .permanentAddress(permanentAddress)
                .currentAddress(currentAddress)
                .officeAddress(officeAddress)
                .build();

        companyRepository.save(company);

        return GlobalResponseHandler.success(
                "Company created successfully",
                Map.of(
                        "code", companyCode,
                        "name", body.companyName()
                ),
                HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Object>> getCompanies(CompanyDTO.CompanySearchRequest filter, Pageable pageable) {

        Specification<Company> specification = CompanySpecifications.fromFilter(filter);
        Page<Company> companies = companyRepository.findAll(specification, pageable);

        return GlobalResponseHandler.success(
                "Companies fetched successfully",
                companies,
                HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getCompanyByCode(String companyCode) {
        Company company = companyRepository.findByCode(companyCode).stream().findFirst()
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Company not found with code: " + companyCode, HttpStatus.NOT_FOUND));

        return GlobalResponseHandler.success("Company fetched successfully", company, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateCompany(String companyCode, CompanyDTO.UpdateCompanyRequestBody body) {
        Company company = companyRepository.findByCode(companyCode).stream().findFirst()
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Company not found with code: " + companyCode, HttpStatus.NOT_FOUND));

        if (body.name() != null) company.setName(body.name());
        if (body.legalName() != null) company.setLegalName(body.legalName());
        if (body.email() != null) company.setEmail(body.email());
        if (body.phone() != null) company.setPhone(body.phone());
        if (body.gstin() != null) company.setGstin(body.gstin());
        if (body.status() != null) company.setStatus(body.status());

        Company savedCompany = companyRepository.save(company);
        return GlobalResponseHandler.success("Company updated successfully", savedCompany, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteCompany(String companyCode) {
        Company company = companyRepository.findByCode(companyCode).stream().findFirst()
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Company not found with code: " + companyCode, HttpStatus.NOT_FOUND));

        companyRepository.delete(company);
        return GlobalResponseHandler.success("Company deleted successfully", null, HttpStatus.OK);
    }
}
