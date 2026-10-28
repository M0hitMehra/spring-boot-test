package com.example.spring_test.utils.DTOResolver;

import com.example.spring_test.models.Address;
import com.example.spring_test.models.AddressType;
import com.example.spring_test.utils.DTO.CompanyDTO;

public class CompanyDTOResolver {

    public static Address toEntity(CompanyDTO.AddressRequestBody dto, AddressType addressType) {
        return Address.builder()
                .addressLine1(dto.line1())
                .addressLine2(dto.line2())
                .city(dto.city())
                .state(dto.state())
                .postalCode(dto.postalCode())
                .country(dto.country())
                .addressType(addressType)
                .build();
    }

}
