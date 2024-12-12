package org.project.ebankify_security.dao;

import org.project.ebankify_security.entity.Transaction;
import org.project.ebankify_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionDAO extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccount_Owner_Id(long userId);

    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount.owner = :owner OR t.destinationAccount.owner = :owner")
    List<Transaction> findUserTransactionHistory(User owner);
}
