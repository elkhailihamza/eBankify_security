package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactionHistory();
    List<TransactionDTO> getAllTransactionHistory();
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    void acceptTransaction(TransactionDTO transactionDTO);
    void refuseTransaction(TransactionDTO transactionDTO);
}
