package org.mdanylko.bankingsolution.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.mdanylko.bankingsolution.annotation.validator.AccountNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AccountNumberValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccountNumber {
    String message() default "Invalid account number. It must be exactly 12 digits.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
