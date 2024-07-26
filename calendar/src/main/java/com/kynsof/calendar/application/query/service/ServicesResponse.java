package com.kynsof.calendar.application.query.service;

import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.dto.enumType.EServiceStatus;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ServicesResponse implements IResponse, Serializable {
    private UUID id;
    private ServiceTypeDto type;
    private EServiceStatus status;
    private String image;
    private String name;
    private String description;
    private boolean applyIva;

    private Double normalAppointmentPrice;
    private Double expressAppointmentPrice;
    private int estimatedDuration;
    private String code;
    private Integer priority;

    public ServicesResponse(ServiceDto object) {
        this.id = object.getId();
        this.type = object.getType();
        this.status = object.getStatus();
        this.image = object.getPicture();
        this.name = object.getName();
        this.description = object.getDescription();
        this.applyIva = object.getApplyIva();
        this.normalAppointmentPrice = object.getNormalAppointmentPrice();
        this.expressAppointmentPrice = object.getExpressAppointmentPrice();
        this.estimatedDuration = object.getEstimatedDuration();
        this.code = object.getCode();
        this.priority = object.getPriority();
    }

}