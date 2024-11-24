package org.mdanylko.bankingsolution.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.mdanylko.bankingsolution.dto.SimpleTransactionDto;
import org.mdanylko.bankingsolution.dto.TransactionResponseDto;
import org.mdanylko.bankingsolution.dto.TransferTransactionDto;
import org.mdanylko.bankingsolution.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Account Transactions", description = "Endpoints for performing transactions on bank accounts")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Deposit funds", description = "Deposits an amount into a specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds deposited successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@Valid @RequestBody SimpleTransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.deposit(transactionDto));
    }

    @Operation(summary = "Withdraw funds", description = "Withdraws an amount from a specified account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds withdrawn successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds")
    })
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDto> withdraw(@Valid @RequestBody SimpleTransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.withdraw(transactionDto));
    }

    @Operation(summary = "Transfer funds", description = "Transfers an amount from one account to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds transferred successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds")
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@Valid @RequestBody TransferTransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.transfer(transactionDto));
    }
}
