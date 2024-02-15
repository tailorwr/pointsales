package com.kynsof.scheduled.application.command.resource.create;

import com.kynsof.scheduled.domain.dto.ResourceDto;
import com.kynsof.scheduled.domain.service.IResourceService;
import com.kynsof.scheduled.infrastructure.config.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateResourceCommandHandler implements ICommandHandler<CreateResourceCommand> {

    private final IResourceService service;

    public CreateResourceCommandHandler(IResourceService service) {
        this.service = service;
    }

    @Override
    public void handle(CreateResourceCommand command) {
        service.create(new ResourceDto(
                command.getId(),
                command.getPicture(),
                command.getName(),
                command.getRegistrationNumber(),
                command.getLanguage(),
                command.getStatus(),
                command.getExpressAppointments()
        ));
    }
}
