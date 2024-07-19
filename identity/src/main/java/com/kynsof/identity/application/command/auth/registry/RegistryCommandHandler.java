package com.kynsof.identity.application.command.auth.registry;


import com.kynsof.identity.domain.interfaces.service.IAuthService;
import com.kynsof.identity.infrastructure.services.kafka.producer.user.ProducerRegisterUserEventService;
import com.kynsof.identity.infrastructure.services.kafka.producer.user.welcom.ProducerUserWelcomEventService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.kafka.entity.UserKafka;
import com.kynsof.share.core.domain.kafka.entity.UserWelcomKafka;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistryCommandHandler implements ICommandHandler<RegistryCommand> {
    private final IAuthService authService;
    private final ProducerRegisterUserEventService producerRegisterUserEventService;
    private final ProducerUserWelcomEventService producerUserWelcomEventService;
    public RegistryCommandHandler(IAuthService authService, ProducerRegisterUserEventService producerRegisterUserEventService, ProducerUserWelcomEventService producerUserWelcomEventService) {
        this.authService = authService;
        this.producerRegisterUserEventService = producerRegisterUserEventService;
        this.producerUserWelcomEventService = producerUserWelcomEventService;
    }

    @Override
    public void handle(RegistryCommand command) {
        String registerUser = authService.registerUser(new UserRequest(
                command.getUsername(), command.getEmail(),command.getFirstname(),
                command.getLastname(),command.getPassword()
        ), false);
        command.setResul(registerUser);

        UserKafka userKafka = new UserKafka();
        userKafka.setId(String.valueOf(UUID.fromString(registerUser)));
        userKafka.setUsername(command.getUsername());
        userKafka.setEmail(command.getEmail());
        userKafka.setFirstname(command.getFirstname());
        userKafka.setLastname(command.getLastname());
        producerRegisterUserEventService.create(userKafka);

        this.producerUserWelcomEventService.create(new UserWelcomKafka(command.getEmail(),
                command.getPassword(),
                command.getEmail(),
                command.getFirstname() + " " + command.getLastname()
        ));
    }
}
