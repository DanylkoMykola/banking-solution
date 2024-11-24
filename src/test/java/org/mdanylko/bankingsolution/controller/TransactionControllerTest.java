package org.mdanylko.bankingsolution.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdanylko.bankingsolution.dto.SimpleTransactionDto;
import org.mdanylko.bankingsolution.dto.TransactionResponseDto;
import org.mdanylko.bankingsolution.dto.TransferTransactionDto;
import org.mdanylko.bankingsolution.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    private TransactionService transactionService;
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        transactionService = mock(TransactionService.class);
        transactionController = new TransactionController(transactionService);
    }

    @Test
    void depositReturnsOkStatus() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        TransactionResponseDto responseDto = new TransactionResponseDto();

        when(transactionService.deposit(transactionDto)).thenReturn(responseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.deposit(transactionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void withdrawReturnsOkStatus() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        TransactionResponseDto responseDto = new TransactionResponseDto();

        when(transactionService.withdraw(transactionDto)).thenReturn(responseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.withdraw(transactionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }



    @Test
    void transferReturnsOkStatus() {
        TransferTransactionDto transactionDto = new TransferTransactionDto();
        TransactionResponseDto responseDto = new TransactionResponseDto();

        when(transactionService.transfer(transactionDto)).thenReturn(responseDto);

        ResponseEntity<TransactionResponseDto> response = transactionController.transfer(transactionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}