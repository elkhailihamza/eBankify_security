package org.project.ebankify_security.dto;

import lombok.*;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.type.TransactionStatus;
import org.project.ebankify_security.entity.type.TransactionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private long id;
    private TransactionType type;
    private double amount;
    private Account sourceAccount;
    private Account destinationAccount;
    private TransactionStatus status = TransactionStatus.PENDING;
}