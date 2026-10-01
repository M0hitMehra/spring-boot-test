package com.example.spring_test.repositories;

import com.example.spring_test.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findByRoleCode(String roles);
}
