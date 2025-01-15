package org.project.ebankify_security.controller;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.LoanDTO;
import org.project.ebankify_security.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<LoanDTO> requestLoan(@RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.requestLoan(loanDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<LoanDTO>> viewUserLoans() {
        return ResponseEntity.ok(loanService.viewUserLoans());
    }

    @GetMapping("/all")
    public ResponseEntity<?> viewAllLoans() {
        return ResponseEntity.ok(loanService.viewAllLoans());
    }
}
