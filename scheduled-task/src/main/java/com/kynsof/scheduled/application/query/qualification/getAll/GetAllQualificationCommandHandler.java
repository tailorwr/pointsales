package com.kynsof.scheduled.application.query.qualification.getAll;

import com.kynsof.scheduled.application.PaginatedResponse;
import com.kynsof.scheduled.domain.service.IQualificationService;
import com.kynsof.scheduled.infrastructure.config.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetAllQualificationCommandHandler implements IQueryHandler<FindQualificationWithFilterQuery, PaginatedResponse>{

    private final IQualificationService service;

    public GetAllQualificationCommandHandler(IQualificationService service) {
        this.service = service;
    }

    @Override
    public PaginatedResponse handle(FindQualificationWithFilterQuery query) {

        return this.service.findAll(query.getPageable(), query.getIdQualification(), query.getFilter());
    }
}
