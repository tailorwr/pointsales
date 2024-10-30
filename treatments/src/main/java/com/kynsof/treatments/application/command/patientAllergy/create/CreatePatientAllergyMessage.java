package com.kynsof.treatments.application.command.patientAllergy.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePatientAllergyMessage implements ICommandMessage {

    private final String command = "CREATE_Allergy";
    private final UUID allergyId;

    public CreatePatientAllergyMessage(UUID allergy) {
        this.allergyId = allergy;
    }

}
