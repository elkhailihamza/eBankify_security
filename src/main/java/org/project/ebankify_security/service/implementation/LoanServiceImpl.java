package org.project.ebankify_security.service.implementation;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.LoanDAO;
import org.project.ebankify_security.dto.mapper.LoanMapper;
import org.project.ebankify_security.dto.response.LoanResDto;
import org.project.ebankify_security.entity.Loan;
import org.project.ebankify_security.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDao;
    private final LoanMapper loanMapper;

    public Loan toLoan(LoanResDto loanResDto) {
        return loanMapper.toLoan(loanResDto);
    }

    public LoanResDto getLoanToLoanResDto(Loan loan) {
        return loanMapper.getLoanToLoanResDto(loan);
    }

    public List<Loan> getLoanHistory() {
        return loanDao.findAll();
    }

    public List<Loan> getUserLoanHistory(long id) {
        return loanDao.findAllByOwner_Id(id);
    }

    public Loan acceptLoan(Loan loan) {
        loan.setApproved(true);
        return saveLoan(loan);
    }

    public Loan refuseLoan(Loan loan) {
        loan.setApproved(false);
        return saveLoan(loan);
    }

    public Optional<Loan> findById(long id) {
        return loanDao.findById(id);
    }

    public Loan saveLoan(Loan loan) {
        return loanDao.save(loan);
    }
}
