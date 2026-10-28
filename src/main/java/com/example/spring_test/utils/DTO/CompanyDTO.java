package com.example.spring_test.utils.DTO;

import com.example.spring_test.models.Company;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public final class CompanyDTO {

    public record CreateCompanyRequestBody(
            @NotBlank(message = "Company name is required")
            @Size(min = 2, max = 150, message = "Company name must be between 2 and 150 characters")
            @Pattern(
                    regexp = "^[A-Za-z0-9 .,&'()\\-]+$",
                    message = "Company name contains invalid characters"
            )
            String companyName,

            @NotBlank(message = "Legal name is required")
            @Size(min = 2, max = 200, message = "Legal name must be between 2 and 200 characters")
            @Pattern(
                    regexp = "^[A-Za-z0-9 .,&'()\\-]+$",
                    message = "Legal name contains invalid characters"
            )
            String legalName,

            @NotBlank(message = "Email is required")
            @Email(message = "Invalid email format")
            @Size(max = 150, message = "Email must not exceed 150 characters")
            String email,

            @NotBlank(message = "Phone is required")
            @Pattern(
                    regexp = "^\\+?[0-9]{7,15}$",
                    message = "Phone must be 7-15 digits, optionally starting with +"
            )
            String phone,

            @NotBlank(message = "GSTIN is required")
            @Pattern(
                    regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
                    message = "Invalid GSTIN format"
            )
            @Size(min = 15, max = 15, message = "GSTIN must be exactly 15 characters")
            String gstin,

            @NotNull(message = "Registered address is required")
            @Valid
            AddressRequestBody registeredAddress,

            @Valid
            AddressRequestBody officeAddress,

            @Valid
            AddressRequestBody currentAddress,

            @NotNull(message = "Permanent address is required")
            @Valid
            AddressRequestBody permanentAddress
    ) {
    }

    public record AddressRequestBody(

            @NotBlank(message = "Address line 1 is required")
            @Size(max = 200, message = "Address line 1 must not exceed 200 characters")
            String line1,

            @Size(max = 200, message = "Address line 2 must not exceed 200 characters")
            String line2,

            @NotBlank(message = "City is required")
            @Size(max = 100, message = "City must not exceed 100 characters")
            String city,

            @NotBlank(message = "State is required")
            @Size(max = 100, message = "State must not exceed 100 characters")
            String state,

            @NotBlank(message = "Postal code is required")
            @Pattern(
                    regexp = "^[1-9][0-9]{5}$",
                    message = "Postal code must be 6 digits (Indian PIN code)"
            )
            String postalCode,

            @NotBlank(message = "Country is required")
            @Size(max = 100, message = "Country must not exceed 100 characters")
            String country
    ) {
    }

    public record CompanySearchRequest(
            String name,
            String code,
            String legalName,
            String email,
            String phone,
            String gstin,
            Company.Status status,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,

            String currentAddressCity,
            String currentAddressState,
            String currentAddressCountry,
            String currentAddressPostalCode,
            String currentAddressLandmark,

            String permanentAddressCity,
            String permanentAddressState,
            String permanentAddressCountry,
            String permanentAddressPostalCode,
            String permanentAddressLandmark,

            String registeredAddressCity,
            String registeredAddressState,
            String registeredAddressCountry,
            String registeredAddressPostalCode,
            String registeredAddressLandmark,

            String officeAddressCity,
            String officeAddressState,
            String officeAddressCountry,
            String officeAddressPostalCode,
            String officeAddressLandmark

    ) {
    }

    public record UpdateCompanyRequestBody(
            String name,
            String legalName,
            String email,
            String phone,
            String gstin,
            Company.Status status
    ) {
    }
}
