package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.ebankify_security.dto.TransactionDTO;
import org.project.ebankify_security.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "sourceAccountNumber", target = "sourceAccount.accountNumber")
    @Mapping(source = "destinationAccountNumber", target = "destinationAccount.accountNumber")
    Transaction toTransaction(TransactionDTO transactionDTO);

    @Mapping(source = "sourceAccount.accountNumber", target = "sourceAccountNumber")
    @Mapping(source = "destinationAccount.accountNumber", target = "destinationAccountNumber")
    TransactionDTO toTransactionDTO(Transaction transaction);
}
