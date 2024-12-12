package org.project.ebankify_security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.project.ebankify_security.entity.type.AccountStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, unique = true)
    private String accountNumber;
    private double balance = 0;

    private LocalDateTime created_at;

    @Column(nullable = false, columnDefinition = "SMALLINT DEFAULT 1")
    private AccountStatus status;

    @PrePersist
    public void prePersist() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }

        if (status == null) {
            status = AccountStatus.ACTIVE;
        }
    }

    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> sentTransactions;
    @OneToMany(mappedBy = "destinationAccount")
    private List<Transaction> receivedTransactions;
}
