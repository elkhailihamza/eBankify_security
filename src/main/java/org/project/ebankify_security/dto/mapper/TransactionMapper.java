package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.request.TransactionReqDto;
import org.project.ebankify_security.dto.response.TransactionResDto;
import org.project.ebankify_security.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toTransaction(TransactionReqDto transactionReqDto);

    TransactionResDto getTransactionToTransactionResDto(Transaction transaction);
}
