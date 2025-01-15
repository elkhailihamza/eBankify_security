package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDao;
    private final TransactionMapper transactionMapper;
    private final AccountDAO accountDao;
    private final UserDAO userDao;

    @Override
    public List<TransactionDTO> getTransactionHistory() {
        long userId = (Long) AuthUtil.getAuthenticationId();
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
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Long userId = (Long) AuthUtil.getAuthenticationId();
        Transaction transaction = transactionMapper.toTransaction(transactionDTO);

        Account srcAccount = accountDao.findAccountByAccountNumber(transaction.getSourceAccount().getAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Source account not found!"));

        Account destAccount = accountDao.findAccountByAccountNumber(transaction.getDestinationAccount().getAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Destination account not found!"));

        validateAccountStatus(srcAccount, userId);
        validateAccountStatus(destAccount, null);
        validateSufficientFunds(srcAccount, transaction.getAmount());

        transaction.setSourceAccount(srcAccount);
        transaction.setDestinationAccount(destAccount);

        if (transaction.getAmount() < 3000) {
            transaction.setStatus(TransactionStatus.ACCEPTED);
            transaction.setType(TransactionType.INSTANT);
        } else {
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setType(TransactionType.STANDARD);
        }

        transaction = transactionDao.save(transaction);

        if (transaction.getStatus() == TransactionStatus.ACCEPTED) {
            acceptTransaction(transactionMapper.toTransactionDTO(transaction));
        }

        return transactionMapper.toTransactionDTO(transaction);
    }

    @Override
    @Transactional
    public void acceptTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionDao.findById(transactionDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found!"));

        transaction.getSourceAccount().setBalance(transaction.getSourceAccount().getBalance() - transaction.getAmount());
        transaction.getDestinationAccount().setBalance(transaction.getDestinationAccount().getBalance() + transaction.getAmount());

        transaction.setStatus(TransactionStatus.ACCEPTED);
        transactionDao.save(transaction);
    }

    @Override
    @Transactional
    public void refuseTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionDao.findById(transactionDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found!"));

        transaction.setStatus(TransactionStatus.REFUSED);
        transactionDao.save(transaction);
    }

    @Override
    public List<TransactionDTO> getAllImportantTransactions() {
        return transactionDao.findImportantTransactions()
                .stream()
                .map(transactionMapper::toTransactionDTO).
                toList();
    }

    private void validateSufficientFunds(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InvalidFundsException("Insufficient Funds!");
        }
    }

    private void validateAccountStatus(Account account, Long userId) {
        if (account.getStatus() == AccountStatus.BLOCKED) {
            throw new EntityRulesViolationException("Account is blocked!");
        }
        if (userId != null && account.getOwner().getId() != userId) {
            throw new EntityRulesViolationException("Unauthorized account access!");
        }
    }
}
