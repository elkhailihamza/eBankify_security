package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.request.TransactionReqDto;
import org.project.ebankify_security.dto.response.TransactionResDto;
import org.project.ebankify_security.entity.Transaction;
import org.project.ebankify_security.entity.User;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction toTransaction(TransactionReqDto transactionReqDto);

    TransactionResDto getTransactionToTransactionResDto(Transaction transaction);

    Optional<Transaction> findTransactionById(long id);

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getUserTransactionHistory(User user);

    List<Transaction> getAllTransactionHistory();
}
