package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.patientVaccine.update.UpdatePatientVaccineMessage;
import com.kynsof.treatments.application.command.vaccine.create.CreateVaccineCommand;
import com.kynsof.treatments.application.command.vaccine.create.CreateVaccineMessage;
import com.kynsof.treatments.application.command.vaccine.create.CreateVaccineRequest;
import com.kynsof.treatments.application.command.vaccine.update.UpdateVaccineCommand;
import com.kynsof.treatments.application.command.vaccine.update.UpdateVaccineRequest;
import com.kynsof.treatments.application.query.vaccine.getById.FindByIdVaccineQuery;
import com.kynsof.treatments.application.query.vaccine.getEligibleVaccines.EligibleVaccinesResponse;
import com.kynsof.treatments.application.query.vaccine.getEligibleVaccines.GetEligibleVaccinesQuery;
import com.kynsof.treatments.application.query.vaccine.getall.GetAllVaccineQuery;
import com.kynsof.treatments.application.query.vaccine.getall.VaccineResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vaccine")
public class VaccineController {

    private final IMediator mediator;

    public VaccineController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateVaccineRequest request)  {
        CreateVaccineCommand createCommand = CreateVaccineCommand.fromRequest(request);
        CreateVaccineMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<PaginatedResponse> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "") String name,
                                                    @RequestParam(defaultValue = "") String description)
    {
        Pageable pageable = PageRequest.of(page, pageSize);
        GetAllVaccineQuery query = new GetAllVaccineQuery(pageable,name, description);
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<VaccineResponse> getById(@PathVariable UUID id) {

        FindByIdVaccineQuery query = new FindByIdVaccineQuery(id);
        VaccineResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/eligible/{patientId}")
    public ResponseEntity<?> getEligibleVaccines( @PathVariable  UUID patientId) {
        GetEligibleVaccinesQuery query = new GetEligibleVaccinesQuery(patientId);
        EligibleVaccinesResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UpdatePatientVaccineMessage> update(@PathVariable UUID id, @RequestBody UpdateVaccineRequest request) {
        UpdateVaccineCommand command = UpdateVaccineCommand.fromRequest(id,request );
        UpdatePatientVaccineMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}