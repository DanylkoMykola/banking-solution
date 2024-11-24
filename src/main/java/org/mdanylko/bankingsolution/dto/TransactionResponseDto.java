package org.mdanylko.bankingsolution.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mdanylko.bankingsolution.annotation.ValidAccountNumber;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    @ValidAccountNumber
    private Long accountNumber;

    private BigDecimal balance;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal transactionAmount;

    @ValidAccountNumber
    private Long transferAccountNumber;

    @NotBlank(message = "Transaction type must not be blank")
    @Pattern(regexp = "^(DEPOSIT|WITHDRAWAL|TRANSFER)$", message = "Transaction type must be DEPOSIT, WITHDRAWAL, or TRANSFER")
    private String transactionType;

    private LocalDateTime transactionTimestamp;
}
