package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Employee;
import com.example.spring_test.repositories.EmployeeRepository;
import com.example.spring_test.utils.DTO.EmployeeDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createEmployee(EmployeeDTO.CreateEmployeeRequest body) {
        if (employeeRepository.findByEmployeeCode(body.employeeCode()).isPresent()) {
            throw new ApiException("ALREADY_EXISTS", "Employee already exists with code: " + body.employeeCode(), HttpStatus.CONFLICT);
        }

        if (employeeRepository.findByEmail(body.email()).isPresent()) {
            throw new ApiException("USER_ALREADY_EXIST", "Employee already exists with email: " + body.email(), HttpStatus.ALREADY_REPORTED);
        }

        Employee employee = Employee.builder()
                .employeeCode(body.employeeCode())
                .firstName(body.firstName())
                .middleName(body.middleName())
                .lastName(body.lastName())
                .email(body.email())
                .phone(body.phone())
                .team_id(body.teamCode() != null ? body.teamCode() : "")
                .designation_id(body.designation() != null ? body.designation() : "")
                .manager_id(body.managerCode() != null ? body.managerCode() : "")
                .password(body.password() != null && !body.password().isBlank() ? passwordEncoder.encode(body.password()) : "")
                .employmentStatus(body.status() != null ? body.status() : Employee.EmploymentStatus.ACTIVE)
                .roles(body.roles() != null ? body.roles() : new ArrayList<>())
                .build();

        Employee saved = employeeRepository.save(employee);
        return GlobalResponseHandler.created("Employee created successfully", saved);
    }

    public ResponseEntity<ApiResponse<Object>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return GlobalResponseHandler.success("Employees fetched successfully", employees, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getEmployeeByCode(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Employee not found with code: " + employeeCode, HttpStatus.NOT_FOUND));

        return GlobalResponseHandler.success("Employee fetched successfully", employee, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateEmployee(String employeeCode, EmployeeDTO.UpdateEmployeeRequest body) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Employee not found with code: " + employeeCode, HttpStatus.NOT_FOUND));

        if (body.firstName() != null) employee.setFirstName(body.firstName());
        if (body.middleName() != null) employee.setMiddleName(body.middleName());
        if (body.lastName() != null) employee.setLastName(body.lastName());
        if (body.email() != null) employee.setEmail(body.email());
        if (body.phone() != null) employee.setPhone(body.phone());
        if (body.teamCode() != null) employee.setTeam_id(body.teamCode());
        if (body.designation() != null) employee.setDesignation_id(body.designation());
        if (body.managerCode() != null) employee.setManager_id(body.managerCode());
        if (body.status() != null) employee.setEmploymentStatus(body.status());
        if (body.roles() != null) employee.setRoles(body.roles());

        Employee updated = employeeRepository.save(employee);
        return GlobalResponseHandler.success("Employee updated successfully", updated, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteEmployee(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Employee not found with code: " + employeeCode, HttpStatus.NOT_FOUND));

        employeeRepository.delete(employee);
        return GlobalResponseHandler.success("Employee deleted successfully", null, HttpStatus.OK);
    }
}
