package com.kynsof.calendar.application.query;

import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.dto.enumType.EServiceStatus;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ServicesResponse implements IResponse {
    private UUID id;
    private ServiceTypeDto type;
    private EServiceStatus status;
    private String image;
    private String name;
    private Double normalAppointmentPrice;
    private Double expressAppointmentPrice;
    private String description;
//    private LocalDateTime createAt;
//    private LocalDateTime updateAt;
//    private LocalDateTime deleteAt;

    public ServicesResponse(ServiceDto object) {
        this.id = object.getId();
        this.type = object.getType();
        this.status = object.getStatus();
        this.image = object.getPicture();
        this.name = object.getName();
        this.normalAppointmentPrice = object.getNormalAppointmentPrice();
        this.expressAppointmentPrice = object.getExpressAppointmentPrice();
        this.description = object.getDescription();
//        this.createAt = object.getCreateAt();
//        this.updateAt = object.getUpdateAt();
//        this.deleteAt = object.getDeleteAt();
    }

}