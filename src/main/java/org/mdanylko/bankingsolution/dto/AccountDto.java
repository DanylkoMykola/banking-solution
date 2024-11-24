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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;

    @ValidAccountNumber
    private Long accountNumber;

    @NotBlank(message = "Owner name must not be blank")
    @Size(max = 100, message = "Owner name must not exceed 100 characters")
    private String ownerName;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", message = "Balance must be greater than or equal to 0")
    private BigDecimal balance;
}