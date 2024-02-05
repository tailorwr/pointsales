package com.kynsof.patients.domain.service;

import com.kynsof.patients.domain.dto.PaginatedResponse;
import com.kynsof.patients.domain.dto.PatientDto;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface IPatientsService {
    UUID create(PatientDto patients);
    UUID update(PatientDto patients);
    void delete(UUID id);
    PatientDto findById(UUID id);
    PaginatedResponse findAll(Pageable pageable, UUID idPatients, String identification);

    void createInsurance(UUID patientId, List<UUID> insuranceIds);
}