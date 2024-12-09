package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.response.LoanResDto;
import org.project.ebankify_security.entity.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan toLoan(LoanResDto loanResDto);

    LoanResDto getLoanToLoanResDto(Loan loan);
}
