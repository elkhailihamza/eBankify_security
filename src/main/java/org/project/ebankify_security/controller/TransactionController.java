package org.project.ebankify_security.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.TransactionDTO;
import org.project.ebankify_security.dto.request.TransactionReqDto;
import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.Transaction;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.EntityRulesViolationException;
import org.project.ebankify_security.exception.InvalidFundsException;
import org.project.ebankify_security.exception.UnexpectedErrorException;
import org.project.ebankify_security.service.AccountService;
import org.project.ebankify_security.service.TransactionService;
import org.project.ebankify_security.service.UserService;
import org.project.ebankify_security.entity.type.AccountStatus;
import org.project.ebankify_security.entity.type.TransactionStatus;
import org.project.ebankify_security.entity.type.TransactionType;
import org.project.ebankify_security.dto.vm.TransactionVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<TransactionDTO>> getAllTransactionHistory() {
        return ResponseEntity.ok(transactionService.getAllTransactionHistory());
    }
}
