package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.AccountDTO;
import org.project.ebankify_security.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void saveAccount(AccountDTO accountDTO);
    void deleteAccount(AccountDTO accountDTO);
    AccountDTO createAccount(AccountDTO accountDTO);
    List<AccountDTO> fetchAllUserAccounts();
    Optional<Account> fetchAccountByAccountNumber(AccountDTO accountDTO);
    String deactivateAccount(AccountDTO accountDTO);
    AccountDTO fetchCertainAccount(AccountDTO accountDTO);
}
