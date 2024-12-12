package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.LoanDTO;
import org.project.ebankify_security.entity.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toLoan(LoanDTO loanDTO);
    LoanDTO toLoanDTO(Loan loan);
}
