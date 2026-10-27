package com.example.spring_test.controller;

import com.example.spring_test.services.AccessLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/access-levels")
@RequiredArgsConstructor
public class AccessLevelController {

    private final AccessLevelService accessLevelService;

    @GetMapping("/init")
    public String init() {
        return accessLevelService.init();
    }
}
