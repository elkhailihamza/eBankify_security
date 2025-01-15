package org.project.ebankify_security.dto;

import lombok.*;
import org.project.ebankify_security.entity.type.AccountStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountDTO {

    private UUID id;
    private String customName;

    private String accountNumber;
    private double balance = 0;
    private LocalDateTime created_at;
    private AccountStatus status;

    private long owner_id;
    private List<TransactionDTO> sentTransactions;
    private List<TransactionDTO> receivedTransactions;
}
