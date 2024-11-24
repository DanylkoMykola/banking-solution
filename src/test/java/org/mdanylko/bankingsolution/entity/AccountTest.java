package org.mdanylko.bankingsolution.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdanylko.bankingsolution.exception.InsufficientFoundsException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(2L,"John Doe", BigDecimal.valueOf(1000));
    }

    @Test
    void subtractBalanceReducesBalance() {
        account.subtractBalance(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), account.getBalance());
    }

    @Test
    void subtractBalanceThrowsExceptionForInsufficientFunds() {
        assertThrows(InsufficientFoundsException.class, () -> account.subtractBalance(BigDecimal.valueOf(1500)));
    }

    @Test
    void addBalanceIncreasesBalance() {
        account.addBalance(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
    }

    @Test
    void subtractBalanceWithExactAmount() {
        account.subtractBalance(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void addBalanceWithZeroAmount() {
        account.addBalance(BigDecimal.ZERO);
        assertEquals(BigDecimal.valueOf(1000), account.getBalance());
    }

    @Test
    void subtractBalanceWithZeroAmount() {
        account.subtractBalance(BigDecimal.ZERO);
        assertEquals(BigDecimal.valueOf(1000), account.getBalance());
    }
}