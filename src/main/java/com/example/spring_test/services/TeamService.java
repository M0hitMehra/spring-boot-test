package com.example.spring_test.services;

import com.example.spring_test.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public String init() {
        return "TeamService initialized";
    }
}
