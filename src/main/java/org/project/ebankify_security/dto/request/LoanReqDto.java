package org.project.ebankify_security.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoanReqDto {

    @NotNull(message = "Principal is required")
    @Positive(message = "Principal must be greater than zero")
    private double principal;

    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be greater than zero")
    private double interestRate;

    @NotNull(message = "Term (in months) is required")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    private int termMonths;
}
