package com.kynsof.patients.application.query.allergy.getall;

import com.kynsof.patients.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetAllAllergyQuery implements IQuery {

    private Pageable pageable;
    private UUID idPatients;
    private String name;
    private String code;

}
