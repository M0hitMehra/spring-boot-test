package com.example.spring_test.services;

import com.example.spring_test.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public String init() {
        return "AddressService initialized";
    }
}
