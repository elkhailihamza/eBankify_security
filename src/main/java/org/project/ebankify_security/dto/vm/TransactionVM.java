package org.project.ebankify_security.dto.vm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.ebankify_security.dto.response.TransactionResDto;

@Getter
@NoArgsConstructor
public class TransactionVM {
    private long id;
    private String type;
    private double amount;
    private String accountSrcNumber;
    private String accountDesNumber;
    private String status;

    public TransactionVM(TransactionResDto transactionResDto) {
        this.id= transactionResDto.getId();
        this.type = transactionResDto.getType().name();
        this.amount = transactionResDto.getAmount();
        this.accountSrcNumber = transactionResDto.getSourceAccount().getAccountNumber();
        this.accountDesNumber = transactionResDto.getDestinationAccount().getAccountNumber();
        this.status = transactionResDto.getStatus().name();
    }
}
