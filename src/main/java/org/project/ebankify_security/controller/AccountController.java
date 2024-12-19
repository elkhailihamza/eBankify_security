package org.project.ebankify_security.controller;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.AccountDTO;
import org.project.ebankify_security.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> viewOwnAccounts() {
        List<AccountDTO> accountDTOs = accountService.fetchAllUserAccounts();
        return ResponseEntity.ok(accountDTOs);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createNewAccount() {
        return ResponseEntity.ok(
                accountService.createAccount()
        );
    }

    @PostMapping("/{accountNumber}/disable")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<String> disableAccount(@PathVariable String accountNumber) {
        AccountDTO accountDTO = AccountDTO.builder().accountNumber(accountNumber).build();
        return ResponseEntity.ok(accountService.deactivateAccount(accountDTO));
    }

    @GetMapping("/{accountNumber}/view")
    public ResponseEntity<AccountDTO> viewCertainAccount(@PathVariable String accountNumber) {
        AccountDTO accountDTO = AccountDTO.builder().accountNumber(accountNumber).build();
        return ResponseEntity.ok(accountService.fetchCertainAccount(accountDTO));
    }

    @PostMapping("/{accountNumber}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        AccountDTO accountDTO = AccountDTO.builder().accountNumber(accountNumber).build();
        accountService.deleteAccount(accountDTO);
        return ResponseEntity.ok("Deleted account!");
    }
}
