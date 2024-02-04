package com.kynsof.scheduled.application.command.resource.update;

import com.kynsof.scheduled.infrastructure.config.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateResourceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_RESOURCE";

    public UpdateResourceMessage(UUID id) {
        this.id = id;
    }

}
