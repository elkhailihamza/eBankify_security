package org.project.ebankify_security.dao;

import org.project.ebankify_security.entity.Account;
import org.project.ebankify_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDAO extends JpaRepository<Account, UUID> {
    boolean existsAccountByAccountNumber(String accountNumber);
    List<Account> findAccountsByOwner_Id(long id);
    List<Account> findAccountsByOwnerEmail(String email);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
