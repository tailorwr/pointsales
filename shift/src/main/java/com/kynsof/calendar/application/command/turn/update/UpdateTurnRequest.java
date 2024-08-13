package com.kynsof.calendar.application.command.turn.update;

import com.kynsof.calendar.domain.dto.enumType.EPriority;
import com.kynsof.calendar.domain.dto.enumType.ETurnStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTurnRequest {
    private UUID doctor;
    private UUID service;
    private UUID business;
    private String identification;
    private EPriority priority;
    private Boolean isPreferential;
    private ETurnStatus status;
}