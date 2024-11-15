package com.kynsof.treatments.application.command.patientAllergy.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePatientAllergyCommand implements ICommand {

    private final UUID id;
    private final UUID patientId;
    private final String cie10;
    private final String severity;
    private final String reaction;
    private final String status;

    public UpdatePatientAllergyCommand(UUID id, UUID patientId, String cie10, String severity, String reaction, String status) {
        this.id = id;
        this.patientId = patientId;
        this.cie10 = cie10;
        this.severity = severity;
        this.reaction = reaction;
        this.status = status;
    }


    public static UpdatePatientAllergyCommand fromRequest(UUID id, UpdatePatientAllergyRequest request) {
        return new UpdatePatientAllergyCommand(id, request.getPatientId(), request.getCie10(), request.getSeverity(),
                request.getReaction(), request.getStatus());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePatientAllergyMessage(true);
    }
}
