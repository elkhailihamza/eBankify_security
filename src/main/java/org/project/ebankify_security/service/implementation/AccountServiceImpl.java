package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.AccountDAO;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.AccountDTO;
import org.project.ebankify_security.dto.mapper.AccountMapper;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.entity.type.AccountStatus;
import org.project.ebankify_security.exception.AccountConflictException;
import org.project.ebankify_security.exception.InvalidFundsException;
import org.project.ebankify_security.service.AccountService;
import org.project.ebankify_security.util.AccountUtil;
import org.project.ebankify_security.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDao;
    private final AccountMapper accountMapper;
    private final UserDAO userDao;

    public void saveAccount(AccountDTO accountDTO) {
        Account account = accountDao.findAccountByAccountNumber(accountDTO.getAccountNumber()).orElseThrow(() -> new EntityNotFoundException("Account not found!"));
        accountDao.save(account);
    }

    public void deleteAccount(AccountDTO accountDTO) {
        Account account = accountDao.findAccountByAccountNumber(accountDTO.getAccountNumber()).orElseThrow(() -> new EntityNotFoundException("Account not found!"));
        accountDao.delete(account);
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Long userId = (Long) AuthUtil.getAuthenticationId();

        User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        int creditScore = user.getCreditScore();
        if (creditScore < 600) {
            throw new InvalidFundsException("Credit Score too low! \nNeeds to be 600 or more.");
        }

        String accountNumber = getUniqueAccountNum(16);
        Account account = Account.builder().accountNumber(accountNumber).build();

        return accountMapper.toAccountDTO(accountDao.save(account));
    }

    public List<AccountDTO> fetchAllUserAccounts() {
        List<Account> accountDTOs = accountDao.findAccountsByOwner_Id((Long) AuthUtil.getAuthenticationId());
        return accountDTOs.stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    private String getUniqueAccountNum(int length) {
        String accountNum;
        do {
            accountNum = AccountUtil.generateAccountNum(length);
        } while(accountDao.existsAccountByAccountNumber(accountNum));
        return accountNum;
    }

    @Override
    public String deactivateAccount(AccountDTO accountDTO) {
        Account account = accountDao.findAccountByAccountNumber(accountDTO.getAccountNumber()).orElseThrow(() -> new EntityNotFoundException("Account not found!"));
        if (account.getStatus() == AccountStatus.BLOCKED) {
            throw new AccountConflictException("Account already blocked!");
        }
        account.setStatus(AccountStatus.BLOCKED);
        accountDao.save(account);
        return "Account blocked successfully!";
    }

    @Override
    public AccountDTO fetchCertainAccount(AccountDTO accountDTO) {
        Account account = accountDao.findAccountByAccountNumber(accountDTO.getAccountNumber()).orElseThrow(() -> new EntityNotFoundException("Account not found!"));
        return accountMapper.toAccountDTO(account);
    }
}
