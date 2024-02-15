package com.kynsof.scheduled.application.command.qualification.create;

import com.kynsof.scheduled.domain.dto.QualificationDto;
import com.kynsof.scheduled.domain.service.IQualificationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateQualificationCommandHandler  implements ICommandHandler<CreateQualificationCommand> {

    private final IQualificationService service;

    public CreateQualificationCommandHandler(IQualificationService service) {
        this.service = service;
    }

    @Override
    public void handle(CreateQualificationCommand command) {
       service.create(new QualificationDto(
                command.getId(),
                command.getDescription()
        ));
    }
}
