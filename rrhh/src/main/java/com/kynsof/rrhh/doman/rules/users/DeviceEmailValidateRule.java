package com.kynsof.rrhh.doman.rules.users;

import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceEmailValidateRule extends BusinessRule {

    private final String email;

    public DeviceEmailValidateRule(String email) {
        super(
                DomainErrorMessage.DEVICE_EMAIL_VALIDATE,
                new ErrorField("User.email", "Direccion de correo incorrecta.")
        );
        this.email = email;
    }

    @Override
    public boolean isBroken() {
        return !isValidEmailAddress(email);
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
