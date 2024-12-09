package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.response.AccountResDto;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void saveAccount(Account account);
    void deleteAccount(Account account);
    Account createAccount(Account newAccount, int accountNumLength);
    List<Account> fetchAllUserAccounts(User owner);
    boolean accountExistsByAccountNumber(String accountNumber);
    Optional<Account> fetchAccountByAccountNumber(String accountNumber);
    AccountResDto getAccountToAccountResDto(Account account);
}
