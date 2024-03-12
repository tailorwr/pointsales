package com.kynsof.patients.application.command.additionalInfo.update;

import com.kynsof.patients.domain.dto.AdditionalInformationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IAdditionalInfoService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.entity.Patients;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateAdditionalInfoCommandHandler implements ICommandHandler<UpdateAdditionalInfoCommand> {

    private final IPatientsService patientsService;
    private final IAdditionalInfoService additionalInfoService;

    public UpdateAdditionalInfoCommandHandler(IPatientsService patientsService, IAdditionalInfoService contactInfoService) {
        this.patientsService = patientsService;
        this.additionalInfoService = contactInfoService;
    }

    @Override
    public void handle(UpdateAdditionalInfoCommand command) {
        PatientDto patientDto = patientsService.findByIdSimple(command.getPatientId());
        additionalInfoService.update(new AdditionalInformationDto(
                command.getId(),
                new Patients(patientDto),
                command.getOccupation(),
                command.getMaritalStatus(),
                command.getEmergencyContactName(),
                command.getEmergencyContactPhone(),
                Status.ACTIVE
        ));

    }
}
