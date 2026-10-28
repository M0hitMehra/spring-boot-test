package com.example.spring_test.repositories;

import com.example.spring_test.models.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Long> {
}
