package com.kynsoft.rrhh.application.command.doctor.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDoctorCommand implements ICommand {

    private UUID id;
    private String identification;
    private String email;
    private String name;
    private String lastName;
    private String status;
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private final String phoneNumber;
    private final String image;
    private String code;

    public UpdateDoctorCommand(UUID id, String identification, String email, String name, String lastName, String status,
                               String registerNumber, String language, boolean isExpress, String phoneNumber, String image,
                               String code) {
        this.id = id;
        this.identification = identification;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.status = status;
        this.registerNumber = registerNumber;
        this.language = language;
        this.isExpress = isExpress;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.code = code;
    }

    public static UpdateDoctorCommand fromRequest(UpdateDoctorRequest request, UUID id) {
        return new UpdateDoctorCommand(
                id,
                request.getIdentification(),
                request.getEmail(),
                request.getName(),
                request.getLastName(),
                request.getStatus(),
                request.getRegisterNumber(),
                request.getLanguage(),
                request.isExpress(),
                request.getPhoneNumber(),
                request.getImage(),
                request.getCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateDoctorMessage(id);
    }
}