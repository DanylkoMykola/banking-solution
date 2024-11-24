package org.mdanylko.bankingsolution.exception;

public class InsufficientFoundsException extends RuntimeException {
    public InsufficientFoundsException(String message) {
        super(message);
    }
}
