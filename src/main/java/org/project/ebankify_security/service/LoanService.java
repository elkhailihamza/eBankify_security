package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.LoanDTO;

import java.util.List;

public interface LoanService {
    LoanDTO acceptLoan(LoanDTO loanDTO);

    LoanDTO refuseLoan(LoanDTO loanDTO);

    LoanDTO requestLoan(LoanDTO loanDTO);

    List<LoanDTO> viewUserLoans();

    List<LoanDTO> viewAllLoans();
}
