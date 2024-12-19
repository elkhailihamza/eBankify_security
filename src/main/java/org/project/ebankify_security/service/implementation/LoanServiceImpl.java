package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.LoanDAO;
import org.project.ebankify_security.dto.LoanDTO;
import org.project.ebankify_security.dto.mapper.LoanMapper;
import org.project.ebankify_security.entity.Loan;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.service.LoanService;
import org.project.ebankify_security.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDao;
    private final LoanMapper loanMapper;
    private final AuthUtil authUtil;

    @Override
    public LoanDTO acceptLoan(LoanDTO loanDTO) {
        Loan loan = loanDao.findById(loanDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Loan not found!"));
        loan.setApproved(true);
        return loanMapper.toLoanDTO(loanDao.save(loan));
    }

    @Override
    public LoanDTO refuseLoan(LoanDTO loanDTO) {
        Loan loan = loanDao.findById(loanDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Loan not found!"));
        loan.setApproved(false);
        return loanMapper.toLoanDTO(loanDao.save(loan));
    }

    @Override
    public LoanDTO requestLoan(LoanDTO loanDTO) {
        Long userId = (Long) authUtil.getAuthenticationId();
        Loan loan = loanMapper.toLoan(loanDTO);
        loan.setOwner(User.builder().id(userId).build());
        return loanMapper.toLoanDTO(loanDao.save(loan));
    }

    @Override
    public List<LoanDTO> viewUserLoans() {
        Long userId = (Long) authUtil.getAuthenticationId();
        List<Loan> loans = loanDao.findAllByOwner_Id(userId);
        return loans.stream()
                .map(loanMapper::toLoanDTO)
                .toList();
    }

    @Override
    public List<LoanDTO> viewAllLoans() {
        return loanDao.findAll().stream()
                .map(loanMapper::toLoanDTO)
                .toList();
    }
}