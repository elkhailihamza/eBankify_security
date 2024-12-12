package org.project.ebankify_security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.project.ebankify_security.entity.Transaction;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.entity.type.AccountStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountDTO {
    public interface Request {};

    private UUID id;
    private String accountNumber;
    private double balance = 0;
    private LocalDateTime created_at;
    private AccountStatus status;

    @NotNull(message = "Owner is required", groups = Request.class)
    private User owner;
    private List<Transaction> sentTransactions;
    private List<Transaction> receivedTransactions;
}
