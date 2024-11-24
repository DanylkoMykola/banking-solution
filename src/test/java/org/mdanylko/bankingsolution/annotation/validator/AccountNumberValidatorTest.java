package org.mdanylko.bankingsolution.annotation.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class AccountNumberValidatorTest {

    private AccountNumberValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new AccountNumberValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void isValidReturnsTrueForValidAccountNumber() {
        assertTrue(validator.isValid(123456789012L, context));
    }

    @Test
    void isValidReturnsFalseForNullAccountNumber() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void isValidReturnsFalseForShortAccountNumber() {
        assertFalse(validator.isValid(12345678L, context));
    }
}