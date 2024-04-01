package com.kynsof.identity.controller;

import com.kynsof.identity.application.command.module.create.CreateModuleCommand;
import com.kynsof.identity.application.command.module.create.CreateModuleMessage;
import com.kynsof.identity.application.command.module.create.CreateModuleRequest;
import com.kynsof.identity.application.query.module.getbyid.FindModuleByIdQuery;
import com.kynsof.identity.application.query.module.getbyid.ModuleResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/module")
public class ModuleController {

    private final IMediator mediator;

    public ModuleController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping()
    public ResponseEntity<CreateModuleMessage> create(@RequestBody CreateModuleRequest request)  {
        CreateModuleCommand createCommand = CreateModuleCommand.fromRequest(request);
        CreateModuleMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ModuleResponse> getById(@PathVariable UUID id) {

        FindModuleByIdQuery query = new FindModuleByIdQuery(id);
        ModuleResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

}
