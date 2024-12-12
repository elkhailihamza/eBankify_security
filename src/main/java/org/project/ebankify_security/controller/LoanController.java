package org.project.ebankify_security.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.LoanDTO;
import org.project.ebankify_security.dto.response.LoanResDto;
import org.project.ebankify_security.entity.Loan;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.service.LoanService;
import org.project.ebankify_security.dto.vm.LoanVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/{loanId}/accept")
    public ResponseEntity<String> acceptLoan(@PathVariable long loanId) {
        LoanDTO loanDTO = LoanDTO.builder().id(loanId).build();
        loanService.acceptLoan(loanDTO);
        return ResponseEntity.ok("Loan accepted successfully!");
    }

    @PostMapping("/{loanId}/refuse")
    public ResponseEntity<?> refuseLoan(@PathVariable long loanId) {
        LoanDTO loanDTO = LoanDTO.builder().id(loanId).build();
        loanService.refuseLoan(loanDTO);
        return ResponseEntity.ok("Loan refused!");
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestLoan(@RequestBody LoanResDto loanResDto, HttpServletRequest request) {
        Long userId = (Long) request.getSession(false).getAttribute("AUTH.id");
        Loan loan = loanService.toLoan(loanResDto);
        loan.setOwner(User.builder().id(userId).build());
        loanService.saveLoan(loan);
        return ResponseEntity.ok("Loan created");
    }

    @GetMapping("/")
    public ResponseEntity<?> viewUserLoan(HttpServletRequest request) {
        Long userId = (Long) request.getSession(false).getAttribute("AUTH.id");
        List<Loan> loans = loanService.getUserLoanHistory(userId);
        List<LoanVM> loanVMs = loans.stream().map(loanService::getLoanToLoanResDto)
                .map(LoanVM::new)
                .toList();
        return ResponseEntity.ok(loanVMs);
    }

    @GetMapping("/all")
    public ResponseEntity<?> viewAllLoans() {
        List<Loan> loans = loanService.getLoanHistory();
        List<LoanVM> loanVMs = loans.stream().map(loanService::getLoanToLoanResDto)
                .map(LoanVM::new)
                .toList();
        return ResponseEntity.ok(loanVMs);
    }
}
