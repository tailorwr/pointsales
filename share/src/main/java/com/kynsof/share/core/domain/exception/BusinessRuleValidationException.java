package com.kynsof.share.core.domain.exception;

import com.kynsof.share.core.domain.rules.BusinessRule;
import lombok.Getter;

@Getter
public class BusinessRuleValidationException extends RuntimeException {

    private final BusinessRule brokenRule;

    private final int status;

    private final String message;

    private final String details;

    public BusinessRuleValidationException(BusinessRule brokenRule) {
        super(brokenRule.getError().getReasonPhrase());

        this.brokenRule = brokenRule;
        this.status = brokenRule.getError().value();
        this.message = brokenRule.getError().getReasonPhrase();
        this.details = brokenRule.getErrorField().getMessage();
    }

    @Override
    public String toString() {
        return String.format("%s: %s", brokenRule.getClass().getName(), this.getMessage());
    }
}
