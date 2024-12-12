package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.LoanDTO;
import org.project.ebankify_security.dto.response.LoanResDto;
import org.project.ebankify_security.entity.Loan;

import java.util.List;

public interface LoanService {
    Loan toLoan(LoanResDto loanResDto);

    LoanResDto getLoanToLoanResDto(Loan loan);

    List<Loan> getLoanHistory();

    List<Loan> getUserLoanHistory(long id);

    LoanDTO acceptLoan(LoanDTO loanDTO);

    LoanDTO refuseLoan(LoanDTO loanDTO);
}
