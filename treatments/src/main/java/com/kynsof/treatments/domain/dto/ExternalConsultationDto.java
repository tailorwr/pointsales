package com.kynsof.treatments.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ExternalConsultationDto {

    private UUID id;
    private PatientDto patient;
    private DoctorDto doctor;
    private Date consultationTime;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private List<DiagnosisDto> diagnoses;
    private List<TreatmentDto> treatments;
    private String observations;
    private ExamOrderDto examOrder;

    public ExternalConsultationDto(UUID id, PatientDto patient, DoctorDto doctor, Date consultationTime,
                                   String consultationReason, String medicalHistory, String physicalExam,
                                   String observations, ExamOrderDto examOrder) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.consultationTime = consultationTime;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observations = observations;
        this.examOrder = examOrder;
    }

}
