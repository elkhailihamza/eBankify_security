package org.project.ebankify_security.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.type.TransactionType;

@Data
public class TransactionReqDto {

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotNull(message = "Source account is required")
    private Account sourceAccount;

    @NotNull(message = "Destination account is required")
    private Account destinationAccount;
}
