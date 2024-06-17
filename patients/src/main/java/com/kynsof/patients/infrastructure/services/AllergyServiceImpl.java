package com.kynsof.patients.infrastructure.services;


import com.kynsof.patients.application.query.allergy.getall.AllergyResponse;
import com.kynsof.patients.domain.dto.AllergyEntityDto;
import com.kynsof.patients.domain.service.IAllergyService;
import com.kynsof.patients.infrastructure.entity.Allergy;
import com.kynsof.patients.infrastructure.repository.command.AllergyWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.AllergyReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AllergyServiceImpl implements IAllergyService {

    @Autowired
    private AllergyWriteDataJPARepository repositoryCommand;

    @Autowired
    private AllergyReadDataJPARepository repositoryQuery;

    @Override
    public UUID create(AllergyEntityDto dto) {
        Allergy allergy =this.repositoryCommand.save(new Allergy(dto));
        return allergy.getId();
    }

    @Override
    public UUID update(Allergy dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }
        Allergy entity = this.repositoryCommand.save(dto);
        return entity.getId();
    }


    @Override
    public AllergyEntityDto findById(UUID id) {
        Optional<Allergy> contactInformation = this.repositoryQuery.findById(id);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
       // throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Contact Information not found.");
        throw new RuntimeException("Patients not found.");
    }

    @Override
    public PaginatedResponse findAll(Pageable pageable ) {
        Page<Allergy> data = this.repositoryQuery.findAll( pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Allergy> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Allergy> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Allergy> data) {
        List<AllergyResponse> allergyResponses = new ArrayList<>();
        for (Allergy p : data.getContent()) {
            allergyResponses.add(new AllergyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(allergyResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
