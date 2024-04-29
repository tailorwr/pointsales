package com.kynsof.identity.application.command.auth.autenticate;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Value
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6587730290416571673L;
    String username;
    String password;
}
