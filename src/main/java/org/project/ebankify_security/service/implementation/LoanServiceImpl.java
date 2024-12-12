package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.LoanDAO;
import org.project.ebankify_security.dto.LoanDTO;
import org.project.ebankify_security.dto.mapper.LoanMapper;
import org.project.ebankify_security.dto.response.LoanResDto;
import org.project.ebankify_security.entity.Loan;
import org.project.ebankify_security.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public LoanDTO acceptLoan(LoanDTO loanDTO) {
        Loan loan = loanDao.findById(loanDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Loan not found!"));
        loan.setApproved(true);
        return loanMapper.toLoanDTO(loanDao.save(loan));
    }

    public LoanDTO refuseLoan(LoanDTO loanDTO) {
        Loan loan = loanDao.findById(loanDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Loan not found!"));
        loan.setApproved(false);
        return loanMapper.toLoanDTO(loanDao.save(loan));
    }
}