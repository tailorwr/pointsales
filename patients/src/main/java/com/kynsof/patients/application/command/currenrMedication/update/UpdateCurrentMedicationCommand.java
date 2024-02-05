package com.kynsof.patients.application.command.currenrMedication.update;

import com.kynsof.patients.domain.bus.command.ICommand;
import com.kynsof.patients.domain.bus.command.ICommandMessage;
import com.kynsof.patients.domain.dto.EStatusPatients;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCurrentMedicationCommand implements ICommand {
    private UUID id;
    private String dosage;
    private String name;
    private EStatusPatients status;


    public UpdateCurrentMedicationCommand(UUID id, String dosage, String name, EStatusPatients  status ) {
        this.id = id;
        this.dosage = dosage;
        this.name = name;
        this.status = status;
    }

    public static UpdateCurrentMedicationCommand fromRequest(UUID id, UpdateCurrentMedicationRequest request) {
        return new UpdateCurrentMedicationCommand(id, request.getDosage(), request.getName(), request.getStatus());
    }


    @Override
    public ICommandMessage getMessage() {
        return new UpdateCurrentMedicationMessage();
    }
}
