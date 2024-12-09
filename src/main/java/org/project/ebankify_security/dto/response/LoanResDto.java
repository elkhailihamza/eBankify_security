package org.project.ebankify_security.dto.response;

import lombok.Data;
import org.project.ebankify_security.entity.User;

@Data
public class LoanResDto {
    private long id;
    private double principal;
    private double interestRate;
    private int termMonths;
    private User owner;
}
