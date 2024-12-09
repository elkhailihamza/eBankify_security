package org.project.ebankify_security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.project.ebankify_security.entity.type.TransactionStatus;
import org.project.ebankify_security.entity.type.TransactionType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private TransactionType type;
    private double amount;

    @ManyToOne
    private Account sourceAccount;
    @ManyToOne
    private Account destinationAccount;
    @Column(nullable = false, columnDefinition = "SMALLINT DEFAULT 1")
    private TransactionStatus status = TransactionStatus.PENDING;
}
