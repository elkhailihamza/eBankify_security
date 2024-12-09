package org.project.ebankify_security.dao;

import org.project.ebankify_security.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDAO extends JpaRepository<Loan, Long> {
    List<Loan> findAllByOwner_Id(long ownerId);
}
