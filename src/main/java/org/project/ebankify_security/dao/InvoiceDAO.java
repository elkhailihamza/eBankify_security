package org.project.ebankify_security.dao;

import org.project.ebankify_security.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDAO extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByOwner_Id(long ownerId);
}
