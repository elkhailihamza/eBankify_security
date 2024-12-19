package org.project.ebankify_security.controller;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.TransactionDTO;
import org.project.ebankify_security.service.AccountService;
import org.project.ebankify_security.service.TransactionService;
import org.project.ebankify_security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createNewTransaction(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDTO));
    }

    @PostMapping("/{transactionId}/accept")
    public ResponseEntity<String> acceptTransaction(@PathVariable long transactionId) {
        TransactionDTO transactionDTO = TransactionDTO.builder().id(transactionId).build();
        transactionService.acceptTransaction(transactionDTO);
        return ResponseEntity.ok("Transaction accepted");
    }

    @PostMapping("/{transactionId}/refuse")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> refuseTransaction(@PathVariable long transactionId) {
        TransactionDTO transactionDTO = TransactionDTO.builder().id(transactionId).build();
        transactionService.refuseTransaction(transactionDTO);
        return ResponseEntity.ok("Transaction refused");
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory() {
        return ResponseEntity.ok(transactionService.getTransactionHistory());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionHistory() {
        return ResponseEntity.ok(transactionService.getAllTransactionHistory());
    }
}
