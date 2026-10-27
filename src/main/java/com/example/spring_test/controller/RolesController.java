package com.example.spring_test.controller;

import com.example.spring_test.services.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RolesService rolesService;

    @GetMapping("/init")
    public String init() {
        return rolesService.init();
    }
}
