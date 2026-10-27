package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Branch;
import com.example.spring_test.models.Department;
import com.example.spring_test.repositories.BranchRepository;
import com.example.spring_test.repositories.DepartmentRepository;
import com.example.spring_test.utils.DTO.DepartmentDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final BranchRepository branchRepository;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createDepartment(DepartmentDTO.CreateDepartmentRequest body) {
        if (departmentRepository.findByCode(body.code()).isPresent()) {
            throw new ApiException("ALREADY_EXISTS", "Department already exists with code: " + body.code(), HttpStatus.CONFLICT);
        }

        Branch branch = branchRepository.findByCode(body.branchCode())
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Branch not found with code: " + body.branchCode(), HttpStatus.NOT_FOUND));

        Department department = Department.builder()
                .code(body.code())
                .name(body.name())
                .branch(branch)
                .description(body.description())
                .status(body.status() != null ? body.status() : Department.Status.ACTIVE)
                .build();

        Department saved = departmentRepository.save(department);
        return GlobalResponseHandler.created("Department created successfully", saved);
    }

    public ResponseEntity<ApiResponse<Object>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return GlobalResponseHandler.success("Departments fetched successfully", departments, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByCode(departmentCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Department not found with code: " + departmentCode, HttpStatus.NOT_FOUND));

        return GlobalResponseHandler.success("Department fetched successfully", department, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateDepartment(String departmentCode, DepartmentDTO.UpdateDepartmentRequest body) {
        Department department = departmentRepository.findByCode(departmentCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Department not found with code: " + departmentCode, HttpStatus.NOT_FOUND));

        if (body.name() != null) department.setName(body.name());
        if (body.description() != null) department.setDescription(body.description());
        if (body.status() != null) department.setStatus(body.status());

        if (body.branchCode() != null && !body.branchCode().isBlank()) {
            Branch branch = branchRepository.findByCode(body.branchCode())
                    .orElseThrow(() -> new ApiException("NOT_FOUND", "Branch not found with code: " + body.branchCode(), HttpStatus.NOT_FOUND));
            department.setBranch(branch);
        }

        Department updated = departmentRepository.save(department);
        return GlobalResponseHandler.success("Department updated successfully", updated, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteDepartment(String departmentCode) {
        Department department = departmentRepository.findByCode(departmentCode)
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Department not found with code: " + departmentCode, HttpStatus.NOT_FOUND));

        departmentRepository.delete(department);
        return GlobalResponseHandler.success("Department deleted successfully", null, HttpStatus.OK);
    }
}
