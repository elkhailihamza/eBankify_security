package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    void deleteAccount(AccountDTO accountDTO);
    AccountDTO createAccount(AccountDTO accountDTO);
    List<AccountDTO> fetchAllUserAccounts();
    String deactivateAccount(AccountDTO accountDTO);
    AccountDTO fetchCertainAccount(AccountDTO accountDTO);
}
