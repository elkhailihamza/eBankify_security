package org.project.ebankify_security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double principal;
    private double interestRate;
    private int termMonths;

    @ManyToOne
    private User owner;
    private boolean approved;
}
