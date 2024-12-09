package org.project.ebankify_security.service.implementation;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.TransactionDAO;
import org.project.ebankify_security.dto.mapper.TransactionMapper;
import org.project.ebankify_security.dto.request.TransactionReqDto;
import org.project.ebankify_security.dto.response.TransactionResDto;
import org.project.ebankify_security.entity.Transaction;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDao;
    private final TransactionMapper transactionMapper;

    public Transaction toTransaction(TransactionReqDto transactionReqDto) {
        return transactionMapper.toTransaction(transactionReqDto);
    }

    public TransactionResDto getTransactionToTransactionResDto(Transaction transaction) {
        return transactionMapper.getTransactionToTransactionResDto(transaction);
    }

    public Optional<Transaction> findTransactionById(long id) {
        return transactionDao.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionDao.save(transaction);
    }

    public List<Transaction> getUserTransactionHistory(User user) {
        return transactionDao.findUserTransactionHistory(user);
    }

    public List<Transaction> getAllTransactionHistory() {
        return transactionDao.findAll();
    }
}
