package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.AccountDAO;
import org.project.ebankify_security.dao.TransactionDAO;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.TransactionDTO;
import org.project.ebankify_security.dto.mapper.TransactionMapper;
import org.project.ebankify_security.dto.vm.TransactionVM;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.Transaction;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.entity.type.AccountStatus;
import org.project.ebankify_security.entity.type.TransactionStatus;
import org.project.ebankify_security.entity.type.TransactionType;
import org.project.ebankify_security.exception.EntityRulesViolationException;
import org.project.ebankify_security.exception.InvalidFundsException;
import org.project.ebankify_security.exception.TransactionFailedException;
import org.project.ebankify_security.service.TransactionService;
import org.project.ebankify_security.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDao;
    private final TransactionMapper transactionMapper;
    private final AccountDAO accountDao;
    private final AuthUtil authUtil;

    public List<TransactionDTO> getTransactionHistory() {
        long userId = (Long) authUtil.getAuthenticationId();
        User user = User.builder().id(userId).build();
        List<Transaction> transactions = transactionDao.findUserTransactionHistory(user);
        return transactions.stream()
                .map(transactionMapper::toTransactionDTO)
                .toList();
    }

    @Override
    public List<TransactionDTO> getAllTransactionHistory() {
        List<Transaction> transactions = transactionDao.findAll();
        return transactions.stream()
                .map(transactionMapper::toTransactionDTO)
                .toList();
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Long userId = (Long) authUtil.getAuthenticationId();
        Transaction transaction = transactionMapper.toTransaction(transactionDTO);
        Account srcAccount = accountDao.findAccountByAccountNumber(transaction.getSourceAccount().getAccountNumber()).orElseThrow(() -> new EntityNotFoundException("Source account not found!"));
        Account destAccount = accountDao.findAccountByAccountNumber(transaction.getDestinationAccount().getAccountNumber()).orElseThrow(() -> new EntityNotFoundException("Destination account not found!"));

        if (srcAccount.getStatus() == AccountStatus.BLOCKED || destAccount.getStatus() == AccountStatus.BLOCKED || srcAccount.getOwner().getId() != userId) {
            throw new EntityRulesViolationException("Transaction creation failed!");
        }

        if (srcAccount.getBalance() < transaction.getAmount()) {
            throw new InvalidFundsException("Insufficient Funds!");
        }

        transaction.setStatus(transaction.getAmount() > 3000 ? TransactionStatus.PENDING : TransactionStatus.ACCEPTED);

        if (transaction.getAmount() > 3000) {
            transaction.setType(TransactionType.INSTANT);
            acceptTransaction(transactionMapper.toTransactionDTO(transaction));
        } else {
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setType(TransactionType.STANDARD);
        }

        transaction.setSourceAccount(srcAccount);
        transaction.setDestinationAccount(destAccount);

        transaction = transactionDao.save(transaction);

        return transactionMapper.toTransactionDTO(transaction);
    }

    @Override
    public void acceptTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionDao.findById(transactionDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Transaction not found!"));

        checkTransactionStatus(transactionDTO);

        transaction.getSourceAccount().setBalance(transaction.getSourceAccount().getBalance() - transaction.getAmount());
        transaction.getDestinationAccount().setBalance(transaction.getDestinationAccount().getBalance() + transaction.getAmount());

        transaction.setStatus(TransactionStatus.ACCEPTED);
        transactionDao.save(transaction);
    }

    @Override
    public void refuseTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionDao.findById(transactionDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Transaction not found!"));

        checkTransactionStatus(transactionDTO);

        transaction.setStatus(TransactionStatus.REFUSED);
        transactionDao.save(transaction);
    }

    private void checkTransactionStatus(TransactionDTO transactionDTO) {
        if (transactionDTO.getStatus() != TransactionStatus.PENDING) {
            throw new TransactionFailedException("Transaction can't be accepted!");
        }
    }
}
