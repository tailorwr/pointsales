package com.kynsof.shift.application.command.tunerSpecialties.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.shift.domain.dto.BusinessDto;
import com.kynsof.shift.domain.dto.ResourceDto;
import com.kynsof.shift.domain.dto.ServiceDto;
import com.kynsof.shift.domain.dto.TurnerSpecialtiesDto;
import com.kynsof.shift.domain.dto.enumType.ETurnerSpecialtiesStatus;
import com.kynsof.shift.domain.service.IBusinessService;
import com.kynsof.shift.domain.service.IResourceService;
import com.kynsof.shift.domain.service.IServiceService;
import com.kynsof.shift.domain.service.ITurnerSpecialtiesService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateTurnerSpecialtiesBulkCommandHandler implements ICommandHandler<CreateTurnerSpecialtiesBulkCommand> {

    private final ITurnerSpecialtiesService turnerSpecialtiesService;
    private final IResourceService resourceService;
    private final IServiceService serviceService;
    private final IBusinessService businessService;

    public CreateTurnerSpecialtiesBulkCommandHandler(ITurnerSpecialtiesService turnerSpecialtiesService,
                                                     IResourceService resourceService,
                                                     IServiceService serviceService, IBusinessService businessService) {

        this.turnerSpecialtiesService = turnerSpecialtiesService;
        this.resourceService = resourceService;
        this.serviceService = serviceService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateTurnerSpecialtiesBulkCommand commandList) {
        List<TurnerSpecialtiesDto> bulk = new ArrayList<>();
        for (CreateTurnerSpecialtiesCommand command : commandList.getCommandList()) {

            ResourceDto doctor = this.resourceService.findById(this.validateUUID(command.getResource()));
            ServiceDto service = this.serviceService.findByIds(this.validateUUID(command.getService()));
            BusinessDto businessDto = this.businessService.findById(this.validateUUID(command.getBusiness()));
            bulk.add(new TurnerSpecialtiesDto(
                    command.getId(),
                    command.getMedicalHistory(),
                    command.getPatient(),
                    command.getIdentification(),
                    doctor,
                    service,
                    this.validateStatus(command.getStatus()),
                    command.getShiftDateTime(),
                    command.getConsultationTime(),
                    businessDto
            ));
        }



        this.turnerSpecialtiesService.create(bulk);
    }


    private UUID validateUUID(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (Exception e) {
            throw new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.UUID_NOT_FORMAT,
                            new ErrorField("id", DomainErrorMessage.UUID_NOT_FORMAT.getReasonPhrase())));
        }
    }

    private ETurnerSpecialtiesStatus validateStatus(String status) {
        try {
            return ETurnerSpecialtiesStatus.valueOf(status);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.STATUS_NOT_FORMAT, new ErrorField("status", DomainErrorMessage.STATUS_NOT_FORMAT.getReasonPhrase())));
        }
    }

}