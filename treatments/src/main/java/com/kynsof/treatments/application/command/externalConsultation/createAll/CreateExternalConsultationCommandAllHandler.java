package com.kynsof.treatments.application.command.externalConsultation.createAll;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.*;
import com.kynsof.treatments.domain.service.IDoctorService;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class CreateExternalConsultationCommandAllHandler implements ICommandHandler<CreateExternalConsultationAllCommand> {

    private final IExternalConsultationService externalConsultationService;
    private final IPatientsService patientsService;
    private final IDoctorService doctorService;
    private final IMedicinesService medicinesService;

    public CreateExternalConsultationCommandAllHandler(IExternalConsultationService externalConsultationService,
                                                       IPatientsService patientsService, IDoctorService doctorService, IMedicinesService medicinesService) {
        this.externalConsultationService = externalConsultationService;
        this.patientsService = patientsService;
        this.doctorService = doctorService;
        this.medicinesService = medicinesService;
    }

    @Override
    public void handle(CreateExternalConsultationAllCommand command) {
        PatientDto patientDto = patientsService.findById(command.getPatientId());
        DoctorDto doctorDto = doctorService.findById(command.getDoctorId());
        List<DiagnosisDto> diagnosisDtoList = command.getDiagnosis().stream().map(diagnosisRequest ->
                new DiagnosisDto(UUID.randomUUID(), diagnosisRequest.getIcdCode(), diagnosisRequest.getDescription())).toList();

      List<TreatmentDto> treatmentDtoList = command.getTreatments().stream().map(treatmentRequest -> {
          MedicinesDto medicinesDto = medicinesService.findById(treatmentRequest.getMedication());

          return new TreatmentDto(
                  UUID.randomUUID(),
                  treatmentRequest.getDescription(),
                  medicinesDto,
                  treatmentRequest.getQuantity(),
                  treatmentRequest.getMedicineUnit()
          );
      }).toList();

        UUID id = externalConsultationService.createAll(new ExternalConsultationDto(
                UUID.randomUUID(),
                patientDto,
                doctorDto,
                new Date(),
                command.getConsultationReason(),
                command.getMedicalHistory(),
                command.getPhysicalExam(),
                diagnosisDtoList,
                treatmentDtoList,
                command.getObservations(),
                null
        ));
        command.setId(id);
    }
}
