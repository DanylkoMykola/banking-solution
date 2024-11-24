package org.mdanylko.bankingsolution.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mdanylko.bankingsolution.annotation.ValidAccountNumber;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    @ValidAccountNumber
    private Long accountNumber;

    private BigDecimal balance;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @ValidAccountNumber
    private Long transferAccountNumber;

    @NotBlank(message = "Transaction type must not be blank")
    @Pattern(regexp = "^(DEPOSIT|WITHDRAWAL|TRANSFER)$", message = "Transaction type must be DEPOSIT, WITHDRAWAL, or TRANSFER")
    private String transactionType;

    private LocalDateTime timestamp;
}
