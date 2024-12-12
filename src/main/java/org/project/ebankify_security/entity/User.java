package org.project.ebankify_security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "\"name\"")
    private String name;
    private String surname;
    private String email;
    private String password;
    private int age;
    private double monthlyIncome = 0;
    private int creditScore = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    @OneToMany(mappedBy = "owner")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "owner")
    private List<Loan> loans;
}
