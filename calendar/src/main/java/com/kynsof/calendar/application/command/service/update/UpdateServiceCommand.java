package com.kynsof.calendar.application.command.service.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateServiceCommand implements ICommand {

    private UUID id;
    private UUID serviceTypeId;
    private String picture;
    private String name;
    private Double normalAppointmentPrice;
    private Double expressAppointmentPrice;
    private String description;

    public UpdateServiceCommand(UUID id, UUID serviceTypeId, String picture, String name, Double normalAppointmentPrice, Double expressAppointmentPrice, String description) {
        this.id = id;
        this.serviceTypeId = serviceTypeId;
        this.picture = picture;
        this.name = name;
        this.normalAppointmentPrice = normalAppointmentPrice;
        this.expressAppointmentPrice = expressAppointmentPrice;
        this.description = description;
    }


    public static UpdateServiceCommand fromRequest(UpdateServiceRequest request) {
        return new UpdateServiceCommand(request.getId(), request.getServiceTypeId(), request.getPicture(), request.getName(), request.getNormalAppointmentPrice(), request.getExpressAppointmentPrice(), request.getDescription());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateServiceMessage(id);
    }
}
