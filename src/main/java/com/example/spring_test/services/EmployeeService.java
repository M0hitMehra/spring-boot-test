package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Designation;
import com.example.spring_test.models.Employee;
import com.example.spring_test.repositories.DesignationRepository;
import com.example.spring_test.repositories.EmployeeRepository;
import com.example.spring_test.repositories.TeamRepository;
import com.example.spring_test.utils.DTO.EmployeeDTO;
import com.example.spring_test.utils.helpers.GlobalHelpers;
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
    private final TeamRepository teamRepository;
    private final DesignationRepository designationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createEmployee(EmployeeDTO.CreateEmployeeRequest body) {
        if (employeeRepository.findByEmail(body.email()).isPresent()) {
            throw new ApiException("USER_ALREADY_EXIST", "Employee already exists with email: " + body.email(), HttpStatus.ALREADY_REPORTED);
        }

        teamRepository.findByCode(body.teamCode())
                .orElseThrow(() -> new ApiException("TEAM_NOT_FOUND", "Team not found with code: " + body.teamCode(), HttpStatus.UNPROCESSABLE_CONTENT));

        Designation designation = designationRepository.findByCode(body.designation())
                .or(() -> designationRepository.findByName(body.designation()))
                .orElseThrow(() -> new ApiException("DESIGNATION_NOT_FOUND", "Designation not found in Designation table: " + body.designation(), HttpStatus.UNPROCESSABLE_CONTENT));

        employeeRepository.findByEmployeeCode(body.managerCode())
                .orElseThrow(() -> new ApiException("MANAGER_NOT_FOUND", "Manager not found with employee code: " + body.managerCode(), HttpStatus.UNPROCESSABLE_CONTENT));

        String employeeCode = GlobalHelpers.generateCode("EMP-" + body.firstName(), 4);
        while (employeeRepository.findByEmployeeCode(employeeCode).isPresent()) {
            employeeCode = GlobalHelpers.generateCode("EMP-" + body.firstName(), 4);
        }

        Employee employee = Employee.builder()
                .employeeCode(employeeCode)
                .firstName(body.firstName())
                .middleName(body.middleName())
                .lastName(body.lastName())
                .email(body.email())
                .phone(body.phone())
                .team_id(body.teamCode())
                .designation_id(designation.getName())
                .manager_id(body.managerCode())
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

        if (body.teamCode() != null && !body.teamCode().isBlank()) {
            teamRepository.findByCode(body.teamCode())
                    .orElseThrow(() -> new ApiException("TEAM_NOT_FOUND", "Team not found with code: " + body.teamCode(), HttpStatus.UNPROCESSABLE_CONTENT));
            employee.setTeam_id(body.teamCode());
        }

        if (body.designation() != null && !body.designation().isBlank()) {
            Designation designation = designationRepository.findByCode(body.designation())
                    .or(() -> designationRepository.findByName(body.designation()))
                    .orElseThrow(() -> new ApiException("DESIGNATION_NOT_FOUND", "Designation not found in Designation table: " + body.designation(), HttpStatus.UNPROCESSABLE_CONTENT));
            employee.setDesignation_id(designation.getName());
        }

        if (body.managerCode() != null && !body.managerCode().isBlank()) {
            employeeRepository.findByEmployeeCode(body.managerCode())
                    .orElseThrow(() -> new ApiException("MANAGER_NOT_FOUND", "Manager not found with employee code: " + body.managerCode(), HttpStatus.UNPROCESSABLE_CONTENT));
            employee.setManager_id(body.managerCode());
        }

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
