package org.project.ebankify_security.dto;

import lombok.*;
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
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private TransactionStatus status;
}