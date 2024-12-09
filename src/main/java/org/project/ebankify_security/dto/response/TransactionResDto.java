package org.project.ebankify_security.dto.response;

import lombok.Data;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.type.TransactionStatus;
import org.project.ebankify_security.entity.type.TransactionType;

@Data
public class TransactionResDto {
    private long id;
    private TransactionType type;
    private double amount;
    private Account sourceAccount;
    private Account destinationAccount;
    private TransactionStatus status;
}
