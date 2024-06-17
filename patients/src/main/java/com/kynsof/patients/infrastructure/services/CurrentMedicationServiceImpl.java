package com.kynsof.patients.infrastructure.services;

import com.kynsof.patients.application.query.currentMedication.getall.CurrentMedicationResponse;
import com.kynsof.patients.domain.dto.CurrentMerdicationEntityDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.ICurrentMedicationService;
import com.kynsof.patients.infrastructure.entity.CurrentMedication;
import com.kynsof.patients.infrastructure.repository.command.CurrentMedicationWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.CurrentMedicationReadDataJPARepository;
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
public class CurrentMedicationServiceImpl implements ICurrentMedicationService {

    @Autowired
    private CurrentMedicationWriteDataJPARepository repositoryCommand;

    @Autowired
    private CurrentMedicationReadDataJPARepository repositoryQuery;

    @Override
    public UUID create(CurrentMerdicationEntityDto dto) {
       CurrentMedication currentMedication = this.repositoryCommand.save(new CurrentMedication(dto));
        return currentMedication.getId();
    }

    @Override
    public UUID update(CurrentMedication dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }
        CurrentMedication entity = this.repositoryCommand.save(dto);

        return dto.getId();
    }


    @Override
    public CurrentMerdicationEntityDto findById(UUID id) {
        Optional<CurrentMedication> currenMedication = this.repositoryQuery.findById(id);
        if (currenMedication.isPresent()) {
            return currenMedication.get().toAggregate();
        }
      //  throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Contact Information not found.");
        throw new RuntimeException("Patients not found.");
    }

    @Override
    public PaginatedResponse findAll(Pageable pageable) {
        Page<CurrentMedication> data = this.repositoryQuery.findAll( pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<CurrentMedication> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<CurrentMedication> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<CurrentMedication> data) {
        List<CurrentMedicationResponse> currentMedicationResponses = new ArrayList<>();
        for (CurrentMedication p : data.getContent()) {
            currentMedicationResponses.add(new CurrentMedicationResponse(p.toAggregate()));
        }
        return new PaginatedResponse(currentMedicationResponses, data.getTotalPages(), data.getNumberOfElements(),
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
