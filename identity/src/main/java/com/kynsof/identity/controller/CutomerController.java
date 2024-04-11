package com.kynsof.identity.controller;

import com.kynsof.identity.application.command.customer.create.CreateCustomerCommand;
import com.kynsof.identity.application.command.customer.create.CreateCustomerMessage;
import com.kynsof.identity.application.command.customer.create.CreateCustomerRequest;
import com.kynsof.identity.application.command.customer.update.UpdateCustomerCommand;
import com.kynsof.identity.application.command.customer.update.UpdateCustomerMessage;
import com.kynsof.identity.application.command.customer.update.UpdateCustomerRequest;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CutomerController {

    private final IMediator mediator;

    public CutomerController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping()
    public ResponseEntity<CreateCustomerMessage> create(@RequestBody CreateCustomerRequest request)  {
        CreateCustomerCommand createCommand = CreateCustomerCommand.fromRequest(request);
        CreateCustomerMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateCustomerMessage> update(@PathVariable("id") UUID id, @RequestBody UpdateCustomerRequest request) {

        UpdateCustomerCommand command = UpdateCustomerCommand.fromRequest(request,id);
        UpdateCustomerMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
