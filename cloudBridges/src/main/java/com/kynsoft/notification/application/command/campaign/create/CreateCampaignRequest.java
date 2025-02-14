package com.kynsoft.notification.application.command.campaign.create;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateCampaignRequest {
    private final String userId;
    private final String code;
    private final LocalDate campaignDate;
    private final String templateId;
    private final String tenantId;
    private final String subject;
}
