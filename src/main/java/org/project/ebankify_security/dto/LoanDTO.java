package org.project.ebankify_security.dto;

import lombok.*;
import org.project.ebankify_security.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private long id;
    private double principal;
    private double interestRate;
    private int termMonths;
    private User owner;
    private boolean approved;
}