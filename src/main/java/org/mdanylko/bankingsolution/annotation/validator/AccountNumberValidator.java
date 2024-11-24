package org.mdanylko.bankingsolution.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mdanylko.bankingsolution.annotation.ValidAccountNumber;

public class AccountNumberValidator implements ConstraintValidator<ValidAccountNumber, Long> {

    @Override
    public boolean isValid(Long accountNumber, ConstraintValidatorContext context) {
        String accountNumberString = String.valueOf(accountNumber);
        return accountNumber != null && accountNumberString.matches("\\d{12}");
    }
}
