package org.mdanylko.bankingsolution.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.mdanylko.bankingsolution.dto.AccountDto;
import org.mdanylko.bankingsolution.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Management", description = "Endpoints for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new account", description = "Creates a new bank account with an initial balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountDto));
    }

    @Operation(summary = "Get account details", description = "Retrieves the details of a specific account by account number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(accountNumber));
    }

    @Operation(summary = "List all accounts", description = "Retrieves a list of all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }
}
