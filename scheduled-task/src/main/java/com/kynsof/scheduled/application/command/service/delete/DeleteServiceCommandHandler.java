package com.kynsof.scheduled.application.command.service.delete;

import com.kynsof.scheduled.domain.service.IServiceService;
import com.kynsof.scheduled.infrastructure.config.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteServiceCommandHandler implements ICommandHandler<ServiceDeleteCommand> {

    private final IServiceService service;

    public DeleteServiceCommandHandler(IServiceService service) {
        this.service = service;
    }

    @Override
    public void handle(ServiceDeleteCommand command) {

        service.delete(command.getId());
    }

}
