package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.response.LoanResDto;
import org.project.ebankify_security.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    Loan toLoan(LoanResDto loanResDto);

    LoanResDto getLoanToLoanResDto(Loan loan);

    List<Loan> getLoanHistory();

    List<Loan> getUserLoanHistory(long id);

    Loan acceptLoan(Loan loan);

    Loan refuseLoan(Loan loan);

    Optional<Loan> findById(long id);

    Loan saveLoan(Loan loan);
}
