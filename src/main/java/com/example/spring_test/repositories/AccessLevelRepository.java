package com.example.spring_test.repositories;

import com.example.spring_test.models.Access_level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLevelRepository extends JpaRepository<Access_level, Long> {

    Access_level findByAccessLevelCode(String roles);
}
