package com.kynsof.treatments.application.query.externalConsultation.getall;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.*;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class ExternalConsultationResponse implements IResponse {

    private UUID id;

    private PatientDto patient;
    private DoctorDto doctor;
    private Date consultationTime;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private List<DiagnosisExternalConsultationResponse> diagnoses = new ArrayList<>();
    private List<TreatmentDto> treatments;
    private String observations;
    private ExamOrderDto examOrder;

    public ExternalConsultationResponse(ExternalConsultationDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient();
        this.consultationReason = dto.getConsultationReason();
        this.medicalHistory = dto.getMedicalHistory();
        this.physicalExam = dto.getPhysicalExam();
        this.consultationTime = dto.getConsultationTime();
        this.doctor = dto.getDoctor();
        this.treatments = dto.getTreatments();
        this.observations = dto.getObservations();
        this.examOrder = dto.getExamOrder();
        this.diagnoses = dto.getDiagnoses().stream()
                .map(DiagnosisExternalConsultationResponse::new)
                .collect(Collectors.toList());

    }

}
