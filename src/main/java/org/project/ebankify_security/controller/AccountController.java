package org.project.ebankify_security.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.response.AccountResDto;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.InvalidFundsException;
import org.project.ebankify_security.service.AccountService;
import org.project.ebankify_security.service.UserService;
import org.project.ebankify_security.entity.type.AccountStatus;
import org.project.ebankify_security.dto.vm.AccountVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> viewUserAccounts(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("AUTH.id");
        Optional<User> owner = userService.findUserById(userId);
        if (owner.isPresent()) {
            List<Account> userAccounts = accountService.fetchAllUserAccounts(owner.get());
            List<AccountVM> accountVMs = userAccounts.stream()
                    .map(accountService::getAccountToAccountResDto)
                    .map(AccountVM::new).toList();
            return ResponseEntity.ok(accountVMs);
        }
        throw new EntityNotFoundException("User not found!");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewAccount(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("AUTH.id");

        Optional<User> user = userService.findUserById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            int creditScore = existingUser.getCreditScore();
            if (creditScore < 600) {
                throw new InvalidFundsException("Credit Score too low! \nNeeds to be 600 or more.");
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AccountVM(accountService.getAccountToAccountResDto(
                            accountService.createAccount(new Account(), 16))));
        }
        throw new EntityNotFoundException("User not found!");
    }

    @PostMapping("/{accountNumber}/disable")
    public ResponseEntity<?> disableAccount(@PathVariable String accountNumber) {
        Optional<Account> account = accountService.fetchAccountByAccountNumber(accountNumber);
        if (account.isPresent()) {
            Account foundAccount = account.get();
            foundAccount.setStatus(AccountStatus.BLOCKED);
            accountService.saveAccount(foundAccount);

            return ResponseEntity.ok("Account - "+accountNumber+" Blocked!");
        }
        throw new EntityNotFoundException("Account specified not found!");
    }

    @GetMapping("/{accountNumber}/view")
    public ResponseEntity<?> viewCertainAccount(@PathVariable String accountNumber) {
        Optional<Account> account = accountService.fetchAccountByAccountNumber(accountNumber);
        if (account.isPresent()) {
            AccountResDto accountResDto = accountService.getAccountToAccountResDto(account.get());
            return ResponseEntity.ok(new AccountVM(accountResDto));
        }
        throw new EntityNotFoundException("Account specified not found!");
    }

    @PostMapping("/{accountNumber}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        Optional<Account> account = accountService.fetchAccountByAccountNumber(accountNumber);
        if (account.isPresent()) {
            accountService.deleteAccount(account.get());
            return ResponseEntity.ok("Successfully deleted account!");
        }
        throw new EntityNotFoundException("Account specified not found!");
    }
}
