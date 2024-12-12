package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.TransactionDTO;
import org.project.ebankify_security.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(TransactionDTO transactionDTO);
    TransactionDTO toTransactionDTO(Transaction transaction);
}
