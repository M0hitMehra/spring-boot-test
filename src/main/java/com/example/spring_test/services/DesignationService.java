package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Designation;
import com.example.spring_test.repositories.DesignationRepository;
import com.example.spring_test.utils.DTO.DesignationDTO;
import com.example.spring_test.utils.helpers.GlobalHelpers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesignationService {

    private final DesignationRepository designationRepository;

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createDesignation(DesignationDTO.CreateDesignationRequest body) {
        if (designationRepository.findByName(body.name()).isPresent()) {
            throw new ApiException("ALREADY_EXISTS", "Designation already exists with name: " + body.name(), HttpStatus.CONFLICT);
        }

        String code = GlobalHelpers.generateCode(body.name(), 4);
        while (designationRepository.findByCode(code).isPresent()) {
            code = GlobalHelpers.generateCode(body.name(), 4);
        }

        Designation designation = Designation.builder()
                .code(code)
                .name(body.name())
                .level(body.level())
                .description(body.description())
                .status(body.status() != null ? body.status() : Designation.Status.ACTIVE)
                .build();

        Designation saved = designationRepository.save(designation);
        return GlobalResponseHandler.created("Designation created successfully", saved);
    }

    public ResponseEntity<ApiResponse<Object>> getAllDesignations() {
        List<Designation> designations = designationRepository.findAll();
        return GlobalResponseHandler.success("Designations fetched successfully", designations, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> getDesignationByCode(String designationCode) {
        Designation designation = designationRepository.findByCode(designationCode)
                .or(() -> designationRepository.findByName(designationCode))
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Designation not found with code: " + designationCode, HttpStatus.NOT_FOUND));

        return GlobalResponseHandler.success("Designation fetched successfully", designation, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateDesignation(String designationCode, DesignationDTO.UpdateDesignationRequest body) {
        Designation designation = designationRepository.findByCode(designationCode)
                .or(() -> designationRepository.findByName(designationCode))
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Designation not found with code: " + designationCode, HttpStatus.NOT_FOUND));

        if (body.name() != null) designation.setName(body.name());
        if (body.level() != null) designation.setLevel(body.level());
        if (body.description() != null) designation.setDescription(body.description());
        if (body.status() != null) designation.setStatus(body.status());

        Designation updated = designationRepository.save(designation);
        return GlobalResponseHandler.success("Designation updated successfully", updated, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteDesignation(String designationCode) {
        Designation designation = designationRepository.findByCode(designationCode)
                .or(() -> designationRepository.findByName(designationCode))
                .orElseThrow(() -> new ApiException("NOT_FOUND", "Designation not found with code: " + designationCode, HttpStatus.NOT_FOUND));

        designationRepository.delete(designation);
        return GlobalResponseHandler.success("Designation deleted successfully", null, HttpStatus.OK);
    }
}
